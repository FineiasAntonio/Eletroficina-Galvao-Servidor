package com.eletroficinagalvao.controledeservico.Repository;


import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface ProdutoRepository extends MongoRepository<Produto, String> {
    List<Produto> findByProdutoLike(String name);

    Set<Produto> findByQuantidadeGreaterThan(int quantidade);



}
