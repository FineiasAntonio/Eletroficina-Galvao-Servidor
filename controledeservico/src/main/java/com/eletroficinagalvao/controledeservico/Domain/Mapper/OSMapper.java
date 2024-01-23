package com.eletroficinagalvao.controledeservico.Domain.Mapper;

import com.eletroficinagalvao.controledeservico.Domain.DTO.CreateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.UpdateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Funcionario;
import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Reserva;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ServicoSituacao;
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
    FuncionarioRepository funcionarioRepository;
 
    public OS map(CreateOSRequestDTO dto) {
        OS ordemdeservico = new OS();

        if (!isValid(dto)) {
            log.error("Ordem de serviço inválida");
            throw new BadRequestException("Ordem de serviço inválida");
        }

        if (!dto.produtosReservados().isEmpty()) {
            ordemdeservico.setId_reserva(reservaMapper.criarReserva(dto.produtosReservados()));
            log.info("Reserva criada, id: %s".formatted(ordemdeservico.getId_reserva().getId_reserva()));
        }

        ordemdeservico.setNome(dto.nome());
        ordemdeservico.setCpf(dto.cpf());
        ordemdeservico.setEndereco(dto.endereco());
        ordemdeservico.setTelefone(dto.telefone());
        ordemdeservico.setEquipamento(dto.equipamento());
        ordemdeservico.setNumeroSerie(dto.numeroSerie());
        ordemdeservico.setServico(dto.servico());
        ordemdeservico.setObs(dto.obs());
        ordemdeservico.setComents(dto.coments());
        ordemdeservico.setDataSaida(Date.valueOf(dto.dataSaida()));
        ordemdeservico.setFuncionario_id(funcionarioRepository.findById(dto.funcionario_id()).get());

        ordemdeservico.setDataEntrada(Date.valueOf(LocalDate.now()));
        ordemdeservico.setSituacao(ServicoSituacao.EM_ANDAMENTO);

        return ordemdeservico;
    }

    public OS updateMap(OS ordemdeservico, UpdateOSRequestDTO dto) {

        if (!isValid(dto)) {
            log.error("Ordem de serviço inválida");
            throw new BadRequestException("Ordem de serviço inválida");
        }

        ordemdeservico.setNome(dto.nome());
        ordemdeservico.setCpf(dto.cpf());
        ordemdeservico.setEndereco(dto.endereco());
        ordemdeservico.setTelefone(dto.telefone());
        ordemdeservico.setEquipamento(dto.equipamento());
        ordemdeservico.setNumeroSerie(dto.numeroSerie());
        ordemdeservico.setServico(dto.servico());
        ordemdeservico.setObs(dto.obs());
        ordemdeservico.setComents(dto.coments());
        ordemdeservico.setDataSaida(Date.valueOf(dto.dataSaida()));
        ordemdeservico.setFuncionario_id(dto.funcionario_id());

        if (dto.status().equals("0")){
            ordemdeservico.setSituacao(ServicoSituacao.CONCLUIDO);
            ordemdeservico.getId_reserva().setAtivo(false);
            ordemdeservico.setDataConclusao(Date.valueOf(LocalDate.now()));
        }
        if (dto.status().equals("1") || dto.status().equals("2")) {
            ordemdeservico.setSituacao(ServicoSituacao.getStatus(Integer.parseInt(dto.status())));
            ordemdeservico.getId_reserva().setAtivo(true);
            ordemdeservico.setDataConclusao(null);
        }


        return ordemdeservico;
    }

    private static boolean isValid(CreateOSRequestDTO dto) {
        if (
                dto == null ||
                dto.nome().trim().isEmpty() ||
                dto.equipamento().trim().isEmpty()
            
        ) {
            return false;
        }
        return true;
    }
    private static boolean isValid(UpdateOSRequestDTO dto) {
        if (
                dto == null ||
                dto.os().trim().isEmpty() ||
                dto.nome().trim().isEmpty() ||
                dto.equipamento().trim().isEmpty() ||
                dto.funcionario_id() == null
        ) {
            return false;
        }
        return true;
    }
}
