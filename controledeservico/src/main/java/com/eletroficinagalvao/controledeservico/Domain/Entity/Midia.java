package com.eletroficinagalvao.controledeservico.Domain.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Midia {

    private String descricao;
    private List<String> links;

}
