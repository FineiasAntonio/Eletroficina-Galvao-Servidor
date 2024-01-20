package com.eletroficinagalvao.controledeservico.Domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    
    public Produto(String produto, String referencia, int quantidade, double precoUnitario) {
        this.produto = produto;
        this.referencia = referencia;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    

}
