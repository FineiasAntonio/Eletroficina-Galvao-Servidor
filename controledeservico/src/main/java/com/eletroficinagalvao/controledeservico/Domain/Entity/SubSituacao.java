package com.eletroficinagalvao.controledeservico.Domain.Entity;

import java.util.Arrays;

public enum SubSituacao {
    AGUARDANDO_RETIRADA (1),
    ENTREGUE (0),
    AGUARDANDO_ORCAMENTO(2),
    AGUARDANDO_MONTAGEM(3),
    MONTADO(4),
    TESTADO(5);

    private final int valor;

    SubSituacao(int i){
        this.valor = i;
    }

    public static SubSituacao getSubStatus(int i){
        return Arrays.stream(SubSituacao.values()).filter(e -> e.valor == i).findFirst().get();
    }

    public int get(){
        return valor;
    }
}
