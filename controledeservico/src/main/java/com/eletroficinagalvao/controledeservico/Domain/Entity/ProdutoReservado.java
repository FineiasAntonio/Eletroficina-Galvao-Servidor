package com.eletroficinagalvao.controledeservico.Domain.Entity;

import lombok.Builder;


public class ProdutoReservado extends Produto{

    int quantidadeNescessaria;


    public ProdutoReservado(Produto produto, int quantidadeNescessaria){
        super(produto);
        this.quantidadeNescessaria = quantidadeNescessaria;
    }

}
