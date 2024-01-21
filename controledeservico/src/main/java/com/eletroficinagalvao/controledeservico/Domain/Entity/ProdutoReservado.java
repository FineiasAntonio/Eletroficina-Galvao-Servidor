package com.eletroficinagalvao.controledeservico.Domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "produtos_reservados")
public class ProdutoReservado {

    @Id
    @Column(name = "id_produto", nullable = false, updatable = false)
    private String id_produto;

    @Column (name = "produto", nullable = false)
    private String produto;
    @Column (name = "referencia")
    private String referencia;
    @Column (name = "quantidadeNescessaria")
    private int quantidadeNescessaria;
    @Column (name = "quantidadeReservada")
    private int quantidadeReservada;
    @Column (name = "preco_unitario")
    private double precoUnitario;
}
