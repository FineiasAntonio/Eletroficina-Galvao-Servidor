package com.eletroficinagalvao.controledeservico.Domain.DTO.Reserva;

import com.eletroficinagalvao.controledeservico.Domain.DTO.Estoque.ProdutoDTO;

import java.util.List;

public record ReservaDTO(
        List<ReservaProdutoExistenteDTO> produtosExistentes,
        List<ProdutoDTO> produtosNovos,
        int maoDeObra
) {
}
