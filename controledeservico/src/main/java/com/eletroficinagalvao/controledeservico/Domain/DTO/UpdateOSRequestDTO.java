package com.eletroficinagalvao.controledeservico.Domain.DTO;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Funcionario;

import java.io.File;
import java.util.List;

public record UpdateOSRequestDTO(
        String os,
        String nome,
        String cpf,
        String endereco,
        String telefone,
        String dataSaida,
        String equipamento,
        String numeroSerie,
        String servico,
        String obs,
        Funcionario funcionario_id,
        String coments,
        List<ProdutoDTO> produtosReservados,
        List<File> imagemEntrada,
        List<File> imagemSaida,
        String status
) {
}