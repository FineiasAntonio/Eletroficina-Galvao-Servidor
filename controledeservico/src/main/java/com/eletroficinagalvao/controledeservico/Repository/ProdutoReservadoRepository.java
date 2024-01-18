package com.eletroficinagalvao.controledeservico.Repository;

import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoReservadoRepository extends JpaRepository<ProdutoReservado, String> {

}
