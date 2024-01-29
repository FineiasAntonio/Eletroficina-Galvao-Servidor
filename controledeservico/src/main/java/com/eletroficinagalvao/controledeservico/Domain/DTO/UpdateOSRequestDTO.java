package com.eletroficinagalvao.controledeservico.Domain.DTO;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Funcionario;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
        String coments,
        boolean concluido,
        String subSituacao,
        List<MultipartFile> imagemEntrada,
        List<MultipartFile> imagemSaida,
        MultipartFile video
) {
}
