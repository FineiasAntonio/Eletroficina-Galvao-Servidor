package com.eletroficinagalvao.controledeservico.Repository;

import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProdutoReservadoRepository extends JpaRepository<ProdutoReservado, String> {

    @Modifying
    @Query("UPDATE ProdutoReservado e SET e.quantidadeReservada = e.quantidadeReservada - :quantidade, e.quantidadeNescessaria = e.quantidadeNescessaria + :quantidade WHERE e.id_produto = :uuid")
    public void reservarExistente(@Param("uuid") String uuid, @Param("quantidade") int quantidade);

    @Modifying
    @Query("UPDATE ProdutoReservado e SET e.quantidadeReservada = e.quantidadeReservada + :quantidade, e.quantidadeNescessaria = e.quantidadeNescessaria - :quantidade WHERE e.id_produto = :uuid")
    public void consumirExistente(@Param("uuid") String uuid, @Param("quantidade") int quantidade);

}
