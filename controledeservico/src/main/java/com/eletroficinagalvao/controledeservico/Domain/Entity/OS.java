package com.eletroficinagalvao.controledeservico.Domain.Entity;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Funcionario;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Reserva;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ServicoSituacao;
import com.eletroficinagalvao.controledeservico.Domain.Entity.SubSituacao;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
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

    @Field("nome")
    private String nome;
    @Field("telefone")
    private String telefone;
    @Field("endereco")
    private String endereco;
    @Field("cpf")
    private String cpf;
    @Field("dataEntrada")
    private Date dataEntrada;
    @Field("dataSaida")
    private Date dataSaida;
    @Field("dataConclusao")
    private Date dataConclusao;
    @Field("dataEntrega")
    private Date dataEntrega;
    @Field("equipamento")
    private String equipamento;
    @Field("numeroSerie")
    private String numeroSerie;
    @Field("obs")
    private String obs;
    @Field("servico")
    private String servico;
    @Field("situacao")
    private ServicoSituacao situacao;
    @Field("subSituacao")
    private SubSituacao subSituacao;
    @Field("imagemEntrada")
    private String imagemEntrada;
    @Field("imagemSaida")
    private String imagemSaida;
    @Field("video")
    private String video;
    @Field("funcionario")
    private Funcionario funcionario;
    @Field("coments")
    private String coments;
    @Field("reserva")
    private Reserva reserva;
    @Field("valorTotal")
    private double valorTotal;

}
