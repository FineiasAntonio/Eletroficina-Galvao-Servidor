package com.eletroficinagalvao.controledeservico.Domain.DTO;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Funcionario;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
        List<ProdutoDTO> produtosReservados
) {
}
