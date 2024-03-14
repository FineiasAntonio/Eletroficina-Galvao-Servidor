package com.eletroficinagalvao.controledeservico.Repository;


import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ProdutoRepository extends MongoRepository<Produto, UUID> {
    List<Produto> findByProdutoLike(String name);

    Set<Produto> findByQuantidadeGreaterThan(int quantidade);

    Optional<Produto> findByProduto(String name);

}
