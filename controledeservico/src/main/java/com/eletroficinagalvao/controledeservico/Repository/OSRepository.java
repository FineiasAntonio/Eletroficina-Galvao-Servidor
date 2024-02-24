package com.eletroficinagalvao.controledeservico.Repository;

import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ServicoSituacao;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OSRepository extends MongoRepository<OS, Integer> {

    List<OS> findBySituacao(ServicoSituacao situacao);

    List<OS> findByNomeLike(String name);

}
