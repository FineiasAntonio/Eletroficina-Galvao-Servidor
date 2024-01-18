package com.eletroficinagalvao.controledeservico.Repository;

import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OSRepository extends JpaRepository<OS, Integer> {

    @Query("SELECT os FROM OS os WHERE os.situacao = AGUARDANDO_PECA")
    public List<OS> getWaitingOrders();

    @Query("SELECT os FROM OS os WHERE os.nome LIKE %:name%")
    public List<OS> getByLikeThisName(@Param("name") String name);

}
