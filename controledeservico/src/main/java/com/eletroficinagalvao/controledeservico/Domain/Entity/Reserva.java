package com.eletroficinagalvao.controledeservico.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "reservas")
public class Reserva {

    @Id
    private UUID id = UUID.randomUUID();

    private int idOS;
    private List<ProdutoReservado> produtos_reservados;
    private boolean ativo;
    private int maoDeObra;

    public Reserva(List<ProdutoReservado> produtos_reservados) {
        this.produtos_reservados = produtos_reservados;
    }

    public Reserva(List<ProdutoReservado> produtos_reservados, boolean ativo) {
        this.produtos_reservados = produtos_reservados;
        this.ativo = ativo;
    }

    public Reserva(int idOS, List<ProdutoReservado> produtos_reservados, boolean ativo, int maoDeObra) {
        this.idOS = idOS;
        this.produtos_reservados = produtos_reservados;
        this.ativo = ativo;
        this.maoDeObra = maoDeObra;
    }
}
