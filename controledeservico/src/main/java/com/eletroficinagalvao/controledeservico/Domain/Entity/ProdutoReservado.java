package com.eletroficinagalvao.controledeservico.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProdutoReservado{

    @Id
    private UUID id;

    private String produto;
    private String referencia;
    private int quantidade;
    private double precoUnitario;
    int quantidadeNescessaria;

    public ProdutoReservado(Produto produto, int quantidadeNescessaria){
        this.id = produto.getId();
        this.produto = produto.getProduto();
        this.referencia = produto.getReferencia();
        this.quantidade = 0;
        this.precoUnitario = produto.getPrecoUnitario();
        this.quantidadeNescessaria = quantidadeNescessaria;
    }

    public ProdutoReservado(String produto, String referencia, int quantidade, double precoUnitario, int quantidadeNescessaria) {
        this.produto = produto;
        this.referencia = referencia;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.quantidadeNescessaria = quantidadeNescessaria;
    }
}
