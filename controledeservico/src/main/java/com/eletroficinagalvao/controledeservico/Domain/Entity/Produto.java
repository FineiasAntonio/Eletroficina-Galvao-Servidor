package com.eletroficinagalvao.controledeservico.Domain.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "estoque")
public class Produto{

        @Id
        private UUID id = UUID.randomUUID();

        private String produto;
        private String referencia;
        private int quantidade;
        private double precoUnitario;

    public Produto(String produto, String referencia, int quantidade, double precoUnitario) {
        this.produto = produto;
        this.referencia = referencia;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public Produto(Produto produto) {
    }
}

