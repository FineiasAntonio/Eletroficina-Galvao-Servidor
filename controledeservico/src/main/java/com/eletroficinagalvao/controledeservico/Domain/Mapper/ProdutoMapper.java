package com.eletroficinagalvao.controledeservico.Domain.Mapper;

import com.eletroficinagalvao.controledeservico.Domain.DTO.Estoque.ProdutoDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import com.eletroficinagalvao.controledeservico.Exception.BadRequestException;
import com.eletroficinagalvao.controledeservico.Exception.NotFoundException;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProdutoMapper {

    @Autowired
    private ProdutoRepository produtoRepository;

    // mapeia pro estoque
    public Produto map(ProdutoDTO dto) {

        if (dto == null || !isValid(dto)) {
            throw new BadRequestException("produto inválido");
        }

        return new Produto(dto.produto(),
                           dto.referencia(), dto.quantidade(),
                           dto.precoUnitario()
        );
    }

    // mapeia para a reserva
    public ProdutoReservado mapReserva(ProdutoDTO dto) {

        if (dto == null || !isValid(dto)) {
            throw new BadRequestException("produto inválido");
        }


        Produto produto = produtoRepository.findByProduto(dto.produto())
                .orElseGet(() -> {
            Produto supplier = map(dto);
            supplier.setQuantidade(0);
            produtoRepository.save(supplier);
            return supplier;
        });

        int quantidadeNescessaria = dto.quantidade();

        return new ProdutoReservado(produto, quantidadeNescessaria);
    }

    // pega do estoque e faz uma nova instancia de produto reservado
    public ProdutoReservado reservar(UUID uuidProduto, int quantidadeNescessaria) {
        Produto produto = produtoRepository.findById(uuidProduto)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
        produto.setQuantidade(0);
        return new ProdutoReservado(produto, quantidadeNescessaria);
    }

    private static boolean isValid(ProdutoDTO dto) {
        if (dto.produto().trim().isEmpty()) {
            throw new BadRequestException("campo inválido");
        }
        return true;
    }

}
