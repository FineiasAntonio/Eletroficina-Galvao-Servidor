package com.eletroficinagalvao.controledeservico.Domain.DTO.Reserva;

import java.util.UUID;

public record ReservaProdutoRequestDTO(
        UUID uuidProduto,
        int quantidade
) {
}
