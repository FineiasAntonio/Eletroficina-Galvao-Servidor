package com.eletroficinagalvao.controledeservico.Domain.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table (name = "estoque")
public class Produto {

    @Id
    @Column (name = "id_produto", nullable = false, updatable = false)
    @GeneratedValue (strategy = GenerationType.UUID)
    private String id_produto;

    @Column (name = "produto", nullable = false)
    private String produto;
    @Column (name = "referencia")
    private String referencia;
    @Column (name = "quantidade", nullable = false)
    private int quantidade;
    @Column (name = "preco_unitario")
    private double precoUnitario;
}
