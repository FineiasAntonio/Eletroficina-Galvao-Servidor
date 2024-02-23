package com.eletroficinagalvao.controledeservico.Domain.DTO.OS;

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
        String subSituacao
) {
}
