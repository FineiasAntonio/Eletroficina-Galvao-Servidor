package com.eletroficinagalvao.controledeservico.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Funcionario;
import com.eletroficinagalvao.controledeservico.Exception.InternalServerErrorException;
import com.eletroficinagalvao.controledeservico.Exception.NotFoundException;
import com.eletroficinagalvao.controledeservico.Repository.FuncionarioRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Qualifier ("FuncionarioService")
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    public List<Funcionario> getAll() {
        return repository.findAll();
    }

    public Funcionario getById(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));
    }

    public List<Funcionario> getByLikeThisName(String name){
        return null;
    }

    @Transactional
    public Funcionario create(Funcionario t) {
        try {
            Funcionario funcionarioCriado = repository.save(t);
            log.info("Funcionário registrado: " + t.getNome());
            return funcionarioCriado;
        } catch (Exception e){
            throw new InternalServerErrorException("Funcionário não registrado" + e.getMessage());
        }
    }

    public void update(String id, Funcionario t) {
        repository.deleteById(t.getId());
        repository.save(t);
    }

    public void delete(String id) {
        repository.deleteById(Integer.valueOf(id));
    }

    
}
