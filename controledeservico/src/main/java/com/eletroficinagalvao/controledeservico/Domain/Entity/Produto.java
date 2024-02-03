package com.eletroficinagalvao.controledeservico.Domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "estoque")
public class Produto{

        @Id
        private String id;

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
}
