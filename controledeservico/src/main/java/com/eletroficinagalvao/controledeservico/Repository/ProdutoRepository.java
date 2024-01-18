package com.eletroficinagalvao.controledeservico.Repository;

import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ProdutoRepository extends JpaRepository<Produto, String> {

    @Query("SELECT produto FROM Produto produto WHERE produto.produto LIKE %:name%")
    public List<Produto> getByLikeThisName(@Param("name") String name);

    @Query("SELECT produto FROM Produto produto WHERE produto.quantidade > 0")
    public Set<Produto> listAvaiableItems();

}
