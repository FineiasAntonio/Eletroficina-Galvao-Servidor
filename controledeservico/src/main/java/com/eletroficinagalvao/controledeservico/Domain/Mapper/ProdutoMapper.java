package com.eletroficinagalvao.controledeservico.Domain.Mapper;

import com.eletroficinagalvao.controledeservico.Domain.DTO.ProdutoDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import com.eletroficinagalvao.controledeservico.Exception.BadRequestException;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto map(ProdutoDTO dto) {

        if (dto == null || !isValid(dto)) {
            throw new BadRequestException("produto inválido");
        }

        return new Produto(dto.produto(),
                         dto.referencia(),
                         dto.quantidade().trim().isEmpty() ? 0 : Integer.parseInt(dto.quantidade()),
                         Double.parseDouble(dto.precoUnitario()));

    }

    public ProdutoReservado mapReserva(ProdutoDTO dto) {

        if (dto == null || !isValid(dto)) {
            throw new BadRequestException("produto inválido");
        }

        String uuid = produtoRepository.findAll()
                .stream()
                .filter(e -> e.getProduto().equals(dto.produto()))
                .findFirst().orElseGet(() -> {
                    Produto produtoSupplier = map(dto);
                    produtoSupplier.setQuantidade(0);
                    return produtoRepository.save(produtoSupplier);
                })
                .getId_produto();
        
        int quantidadeNescessaria = dto.quantidade().trim().isEmpty() ? 0 : Integer.parseInt(dto.quantidade());
        int quantidadeReservada = dto.quantidade().trim().isEmpty() ? 0 : Integer.parseInt(dto.quantidade()) * -1;

        return ProdutoReservado.builder()
                .id_produto(uuid)
                .produto(dto.produto())
                .referencia(dto.referencia())
                .quantidadeNescessaria(quantidadeNescessaria)
                .quantidadeReservada(quantidadeReservada)
                .precoUnitario(Double.parseDouble(dto.precoUnitario()))
                .build();
    }

    private static boolean isValid(ProdutoDTO dto) {
        if (dto.produto().trim().isEmpty()) {
            throw new BadRequestException("campo inválido");
        }
        return true;
    }

}
