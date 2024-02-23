package com.eletroficinagalvao.controledeservico.Domain.Entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "ordensdeservico")
@ToString
public class OS {

    private static int controleId = 1;

    @Id
    private int id = controleId++;

    public static void setControleId(int controleId) {
        OS.controleId = controleId;
    }

    private String nome;
    private String telefone;
    private String endereco;
    private String cpf;
    private Date dataEntrada;
    private Date dataSaida;
    private Date dataConclusao;
    private Date dataEntrega;
    private String equipamento;
    private String numeroSerie;
    private String observacao;
    private String servico;
    private ServicoSituacao situacao;
    private SubSituacao subSituacao;
    private String imagemEntrada;
    private String imagemSaida;
    private String video;
    private Funcionario funcionario;
    private String comentarios;
    private Reserva reserva;
    private double valorTotal;
}
