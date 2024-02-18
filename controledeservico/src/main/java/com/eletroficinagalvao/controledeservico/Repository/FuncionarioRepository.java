package com.eletroficinagalvao.controledeservico.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Funcionario;

public interface FuncionarioRepository extends MongoRepository<Funcionario, Integer> {
    
}
