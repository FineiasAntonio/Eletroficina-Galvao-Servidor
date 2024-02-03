package com.eletroficinagalvao.controledeservico.Domain.DTO;

import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;

import java.util.List;

public record ReservaResponseDTO(
    String uuid,
    List<ProdutoReservado> produtos,
    boolean ativo,
    int os,
    String nomeCliente
) {
    
}
