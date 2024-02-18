package com.eletroficinagalvao.controledeservico.Domain.DTO.OS;

import com.eletroficinagalvao.controledeservico.Domain.DTO.Estoque.ProdutoDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.Reserva.ReservaProdutoRequestDTO;

import java.util.List;

public record CreateOSRequestDTO(
        String nome,
        String cpf,
        String endereco,
        String telefone,
        String dataSaida,
        String equipamento,
        String numeroSerie,
        String servico,
        String obs,
        int funcionario_id,
        String coments,
        List<ReservaProdutoRequestDTO> produtosReservados,
        List<ProdutoDTO> novoProdutoReservado
) {
}
