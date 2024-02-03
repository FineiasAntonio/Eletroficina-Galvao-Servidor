package com.eletroficinagalvao.controledeservico.Domain.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Entity
@Document(collection = "funcionarios")
public class Funcionario {
    
    @Id
    private int id;

    private String nome;

}
