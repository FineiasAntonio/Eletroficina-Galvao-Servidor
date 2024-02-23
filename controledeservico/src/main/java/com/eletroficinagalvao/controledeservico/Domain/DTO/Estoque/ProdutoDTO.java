package com.eletroficinagalvao.controledeservico.Domain.DTO.Estoque;

public record ProdutoDTO(
        String produto,
        String referencia,
        int quantidade,
        double precoUnitario
) {}
