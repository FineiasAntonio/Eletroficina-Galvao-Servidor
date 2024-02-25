package com.eletroficinagalvao.controledeservico.Domain.Mapper;

import com.eletroficinagalvao.controledeservico.Config.OSIDControlConfig;
import com.eletroficinagalvao.controledeservico.Domain.DTO.OS.CreateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.OS.UpdateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ServicoSituacao;
import com.eletroficinagalvao.controledeservico.Domain.Entity.SubSituacao;
import com.eletroficinagalvao.controledeservico.Exception.BadRequestException;
import com.eletroficinagalvao.controledeservico.Repository.FuncionarioRepository;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@Log4j2
public class OSMapper {
    @Autowired
    private ReservaMapper reservaMapper;
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public OS map(CreateOSRequestDTO dto) {
        OS ordemdeservico = new OS();
        ordemdeservico.setId(OSIDControlConfig.idAtual++);

        if (!isValid(dto)) {
            throw new BadRequestException("Ordem de serviço inválida");
        }

        ordemdeservico.setReserva(reservaMapper.criarReserva(
                dto.produtosReservados(),
                dto.novoProdutoReservado(),
                ordemdeservico.getId()
        ).orElse(null));
        log.info("Reserva criada, id: %s".formatted(ordemdeservico.getReserva().getId()));

        ordemdeservico.setNome(dto.nome());
        ordemdeservico.setCpf(dto.cpf());
        ordemdeservico.setEndereco(dto.endereco());
        ordemdeservico.setTelefone(dto.telefone());
        ordemdeservico.setEquipamento(dto.equipamento());
        ordemdeservico.setNumeroSerie(dto.numeroSerie());
        ordemdeservico.setServico(dto.servico());
        ordemdeservico.setObservacao(dto.observacao());
        ordemdeservico.setObservacao(dto.comentarios());
        ordemdeservico.setDataSaida(Date.valueOf(dto.dataSaida()));
        ordemdeservico.setFuncionario(funcionarioRepository.findById(dto.funcionarioId()).get());

        ordemdeservico.setDataEntrada(Date.valueOf(LocalDate.now()));

        if ((ordemdeservico.getReserva() != null) && ordemdeservico.getReserva().isAtivo()) {
            ordemdeservico.setSituacao(ServicoSituacao.AGUARDANDO_PECA);
        } else {
            ordemdeservico.setSituacao(ServicoSituacao.EM_ANDAMENTO);
        }

        return ordemdeservico;
    }

    public OS updateMap(OS ordemdeservico, UpdateOSRequestDTO dto) {

        if (!isValid(dto)) {
            throw new BadRequestException("Ordem de serviço inválida");
        }


        ordemdeservico.setNome(dto.nome());
        ordemdeservico.setCpf(dto.cpf());
        ordemdeservico.setEndereco(dto.endereco());
        ordemdeservico.setTelefone(dto.telefone());
        ordemdeservico.setEquipamento(dto.equipamento());
        ordemdeservico.setNumeroSerie(dto.numeroSerie());
        ordemdeservico.setServico(dto.servico());
        ordemdeservico.setObservacao(dto.observacao());
        ordemdeservico.setComentarios(dto.comentarios());
        ordemdeservico.setDataSaida(Date.valueOf(dto.dataSaida()));
        ordemdeservico.setSubSituacao(SubSituacao.getSubStatus(Integer.parseInt(dto.subSituacao())));
        ordemdeservico.setFuncionario(funcionarioRepository.findById(dto.funcionarioId()).get());

        if (dto.concluido()) {
            ordemdeservico.setSituacao(ServicoSituacao.CONCLUIDO);
        } else {
            if (ordemdeservico.getReserva().isAtivo()) {
                ordemdeservico.setSituacao(ServicoSituacao.AGUARDANDO_PECA);
            } else {
                ordemdeservico.setSituacao(ServicoSituacao.EM_ANDAMENTO);
            }
        }

        return ordemdeservico;
    }

    private static boolean isValid(CreateOSRequestDTO dto) {
        return dto != null &&
                !dto.nome().trim().isEmpty() &&
                !dto.equipamento().trim().isEmpty();
    }

    private static boolean isValid(UpdateOSRequestDTO dto) {
        return dto != null &&
                !dto.nome().trim().isEmpty() &&
                !dto.equipamento().trim().isEmpty();
    }
}
