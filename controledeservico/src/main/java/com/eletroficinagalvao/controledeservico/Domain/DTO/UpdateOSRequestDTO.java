package com.eletroficinagalvao.controledeservico.Domain.DTO;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Funcionario;

import java.io.File;
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
        String obs,
        int funcionario_id,
        String coments
) {
}
