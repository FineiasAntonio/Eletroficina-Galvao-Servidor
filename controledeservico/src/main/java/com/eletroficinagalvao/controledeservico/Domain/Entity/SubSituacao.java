package com.eletroficinagalvao.controledeservico.Domain.Entity;

import java.util.Arrays;

public enum SubSituacao {
    AGUARDANDO_RETIRADA (1),
    ENTREGUE (0),
    MONTADO(2),
    TESTADO(3),
    APROVADO(4);

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
