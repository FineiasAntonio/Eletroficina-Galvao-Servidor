 package com.eletroficinagalvao.controledeservico.Domain.DTO;

import lombok.Builder;

@Builder
public record NotificationDTO(
        String uuid,
        String nomeCliente,
        int orderID,
        String produto,
        int quantidade
) {
}
