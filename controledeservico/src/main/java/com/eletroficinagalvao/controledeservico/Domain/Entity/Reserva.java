package com.eletroficinagalvao.controledeservico.Domain.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Builder
@Table (name = "reservas")
public class Reserva {

    @Id
    @Column (name = "id_reserva")
    @GeneratedValue (strategy = GenerationType.UUID)
    private String id_reserva;

    @JoinTable (
            name = "reserva_produto",
            joinColumns = @JoinColumn (name = "id_reserva"),
            inverseJoinColumns = @JoinColumn (name = "id_produto")
    )
    @ManyToMany
    private List<ProdutoReservado> produtos_reservados;

    @Column(name = "ativo")
    private boolean ativo;

}
