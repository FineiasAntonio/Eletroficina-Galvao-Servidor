package com.eletroficinagalvao.controledeservico.Domain.DTO.Reserva;

import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;

import java.util.List;
import java.util.UUID;

public record ReservaResponseDTO(
    UUID uuid,
    List<ProdutoReservado> produtos,
    boolean ativo,
    int os,
    String nomeCliente
) {
    
}
