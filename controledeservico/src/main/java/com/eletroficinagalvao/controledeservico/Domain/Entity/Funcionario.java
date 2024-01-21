package com.eletroficinagalvao.controledeservico.Domain.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "funcionarios")
public class Funcionario {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "nome")
    private String nome;

}
