package com.eletroficinagalvao.controledeservico.Domain.DTO.OS;

import com.eletroficinagalvao.controledeservico.Domain.DTO.Estoque.ProdutoDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.Reserva.ReservaProdutoRequestDTO;

import java.util.List;

public record UpdateOSRequestDTO(
        String nome,
        String cpf,
        String endereco,
        String telefone,
        String dataSaida,
        String equipamento,
        String numeroSerie,
        String servico,
        String observacao,
        int funcionarioId,
        String comentarios,
        boolean concluido,
        int subSituacao,
        List<ReservaProdutoRequestDTO> produtosReservados,
        List<ProdutoDTO> novoProdutoReservado
) {
}
