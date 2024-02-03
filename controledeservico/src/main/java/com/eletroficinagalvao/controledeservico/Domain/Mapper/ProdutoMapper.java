package com.eletroficinagalvao.controledeservico.Domain.Mapper;

import com.eletroficinagalvao.controledeservico.Domain.DTO.ProdutoDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import com.eletroficinagalvao.controledeservico.Exception.BadRequestException;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

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

        UUID uuid = produtoRepository.findAll()
                .stream()
                .filter(e -> e.getProduto().equals(dto.produto()))
                .findFirst().orElseGet(() -> {
                    Produto produtoSupplier = map(dto);
                    produtoSupplier.setQuantidade(0);
                    return produtoRepository.save(produtoSupplier);
                })
                .getId();
        
        int quantidadeNescessaria = Integer.parseInt(dto.quantidade());
        int quantidadeReservada = Integer.parseInt(dto.quantidade()) * -1;

        return new ProdutoReservado(Produto.builder()
                .id(uuid)
                .produto(dto.produto())
                .referencia(dto.referencia())
                .quantidade(quantidadeReservada)
                .precoUnitario(Double.parseDouble(dto.precoUnitario()))
                .build(), quantidadeNescessaria);
    }

    private static boolean isValid(ProdutoDTO dto) {
        if (dto.produto().trim().isEmpty()) {
            throw new BadRequestException("campo inválido");
        }
        return true;
    }

}
