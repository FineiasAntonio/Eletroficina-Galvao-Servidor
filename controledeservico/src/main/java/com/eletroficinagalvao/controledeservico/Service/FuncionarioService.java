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
public class FuncionarioService implements Services<Funcionario>{

    @Autowired
    private FuncionarioRepository repository;

    @Override
    public List<Funcionario> getAll() {
        return repository.findAll();
    }

    @Override
    public Funcionario getById(String id) {
        return repository.findById(Integer.valueOf(id)).orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));
    }

    @Override
    public List<Funcionario> getByLikeThisName(String name){
        return null;
    }

    @Override
    @Transactional
    public void create(Funcionario t) {
        try {
            t.setServicos(0);
            repository.save(t);
            log.info("Funcionário registrado: " + t.getNome());
        } catch (Exception e){
            throw new InternalServerErrorException("Funcionário não registrado" + e.getMessage());
        }
    }

    @Override
    public void update(String id, Funcionario t) {
        repository.deleteById(t.getId());
        repository.save(t);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(Integer.valueOf(id));
    }


    
}
