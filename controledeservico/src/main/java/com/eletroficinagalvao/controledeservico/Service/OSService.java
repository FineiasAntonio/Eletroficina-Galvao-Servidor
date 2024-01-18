package com.eletroficinagalvao.controledeservico.Service;

import com.eletroficinagalvao.controledeservico.Domain.DTO.CreateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.UpdateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import com.eletroficinagalvao.controledeservico.Domain.Mapper.OSMapper;
import com.eletroficinagalvao.controledeservico.Exception.NotFoundException;
import com.eletroficinagalvao.controledeservico.Repository.FuncionarioRepository;
import com.eletroficinagalvao.controledeservico.Repository.OSRepository;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@Qualifier ("OSService")
public class OSService {

    @Autowired
    private OSRepository repository;
    @Autowired
    private FuncionarioRepository repositoryFuncionario;
    @Autowired
    private OSMapper mapper;

    public List<OS> getAll(){
        return repository.findAll();
    }

    public OS getById(String id){
        return repository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new NotFoundException("Não foi encontrado essa ordem de serviço"));
    }

    @Transactional
    public void create(CreateOSRequestDTO ordemdeservico){
        OS os = mapper.map(ordemdeservico);
        repository.save(os);
        log.info("Ordem de serviço registrada no nome de: " + repositoryFuncionario.findById(os.getFuncionario_id().getId()));
    }

    @Transactional
    public void delete(String id){
        repository.deleteById(Integer.valueOf(id));
    }

    @Transactional
    public void update(int id, UpdateOSRequestDTO os){
        OS updatedOS = mapper.updateMap(getById(String.valueOf(id)), os);
        repository.save(updatedOS);
    }
}