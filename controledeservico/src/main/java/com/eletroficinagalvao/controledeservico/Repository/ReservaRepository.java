package com.eletroficinagalvao.controledeservico.Repository;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Reserva;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservaRepository extends MongoRepository<Reserva, UUID> {

    public Reserva findByIdOS(int idOS);

    public List<Reserva> findByAtivo(boolean ativo);

}
