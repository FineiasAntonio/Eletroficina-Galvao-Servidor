package com.eletroficinagalvao.controledeservico.Domain.DTO;

import java.util.UUID;

public record ReservarProdutoDTO(
        UUID uuidProduto,
        int quantidadeNescessaria
) {
}
