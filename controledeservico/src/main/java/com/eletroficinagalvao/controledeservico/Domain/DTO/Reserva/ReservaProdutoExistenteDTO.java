package com.eletroficinagalvao.controledeservico.Domain.DTO.Reserva;

import java.util.UUID;

public record ReservaProdutoExistenteDTO(
        UUID uuidProduto,
        int quantidade
) {
}
