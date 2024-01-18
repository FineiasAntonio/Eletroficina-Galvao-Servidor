package com.eletroficinagalvao.controledeservico.Domain.Entity;

import java.util.Arrays;

public enum ServicoSituacao {
    CONCLUIDO( 0),
    EM_ANDAMENTO(1),
    AGUARDANDO_PECA(2);

    private final int valor;

    ServicoSituacao(int i) {
        this.valor = i;
    }

    public static ServicoSituacao getStatus(int i){
        return Arrays.stream(ServicoSituacao.values()).filter(e -> e.valor == i).findFirst().get();
    }
}
