package com.eletroficinagalvao.controledeservico.Domain.DTO;

import lombok.Builder;

@Builder
public record NotificationDTO(
        String uuid,
        int orderID,
        String produto,
        int quantidade
) {
}
