package com.eletroficinagalvao.controledeservico.Domain.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@Entity
@ToString
@Table (name = "ordemdeservico")
public class OS {

    @Id
    @Column (name = "os")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int os;

    @Column (name = "nome")
    private String nome;

    @Column (name = "telefone")
    private String telefone;

    @Column (name = "endereco")
    private String endereco;

    @Column (name = "cpf")
    private String cpf;

    @Column (name = "data_entrada")
    private Date dataEntrada;

    @Column (name = "data_saida")
    private Date dataSaida;

    @Column (name = "data_conclusao")
    private Date dataConclusao;

    @Column (name = "data_entrega")
    private Date dataEntrega;

    @Column (name = "equipamento")
    private String equipamento;

    @Column (name = "numero_serie")
    private String numeroSerie;

    @Column (name = "obs")
    private String obs;

    @Column (name = "servico")
    private String servico;

    @Column (name = "situacao")
    @Enumerated (EnumType.ORDINAL)
    private ServicoSituacao situacao;

    @Column (name = "sub_situacao")
    @Enumerated (EnumType.ORDINAL)
    private SubSituacao subSituacao;

    @Column (name = "imagem_entrada")
    private String imagemEntrada;

    @Column (name = "imagem_saida")
    private String imagemSaida;

    @Column (name = "video")
    private String video;

    @JoinColumn (name = "funcionario_id")
    @ManyToOne
    private Funcionario funcionario_id;

    @Column (name = "coments")
    private String coments;

    @JoinColumn (name = "id_reserva")
    @OneToOne
    private Reserva id_reserva;

    @Column (name = "valor_total")
    private double valorTotal;

}
