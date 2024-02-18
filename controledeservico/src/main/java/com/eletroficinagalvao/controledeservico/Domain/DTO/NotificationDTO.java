 package com.eletroficinagalvao.controledeservico.Domain.DTO;

import lombok.Builder;

import java.util.UUID;

 @Builder
public record NotificationDTO(
        UUID uuid,
        String nomeCliente,
        int orderID,
        String produto,
        int quantidade
) {
}
