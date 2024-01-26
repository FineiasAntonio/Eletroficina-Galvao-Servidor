package com.eletroficinagalvao.controledeservico.Repository;

import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProdutoReservadoRepository extends JpaRepository<ProdutoReservado, String> {

    @Query("SELECT e FROM ProdutoReservado e WHERE e.produto = :nome")
    public Optional<ProdutoReservado> findByNome(@Param("nome") String nome);

    @Modifying
    @Query("UPDATE ProdutoReservado e SET e.quantidadeReservada = e.quantidadeReservada - :quantidade, e.quantidadeNescessaria = e.quantidadeNescessaria + :quantidade WHERE e.id_produto = :uuid")
    public void reservarExistente(@Param("uuid") String uuid, @Param("quantidade") int quantidade);

    @Modifying
    @Query("UPDATE ProdutoReservado e SET e.quantidadeReservada = e.quantidadeReservada + :quantidade, e.quantidadeNescessaria = e.quantidadeNescessaria - :quantidade WHERE e.id_produto = :uuid")
    public void consumirExistente(@Param("uuid") String uuid, @Param("quantidade") int quantidade);

    @Transactional
    @Modifying
    @Query(value = "UPDATE produto_reservado SET id_produto = :novoId WHERE id_produto = :idAntigo", nativeQuery = true)
    void alterarId(@Param("idAntigo") String idAntigo, @Param("novoId") String novoId);


}
