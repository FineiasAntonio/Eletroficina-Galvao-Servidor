package com.eletroficinagalvao.controledeservico.Domain.Entity;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Funcionario;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Reserva;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ServicoSituacao;
import com.eletroficinagalvao.controledeservico.Domain.Entity.SubSituacao;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
    private String obs;
    private String servico;
    private ServicoSituacao situacao;
    private SubSituacao subSituacao;
    private String imagemEntrada;
    private String imagemSaida;
    private String video;
    private Funcionario funcionario;
    private String coments;
    private Reserva reserva;
    private double valorTotal;

}
