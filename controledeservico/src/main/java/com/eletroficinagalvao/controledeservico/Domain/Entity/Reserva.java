package com.eletroficinagalvao.controledeservico.Domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public Reserva(List<ProdutoReservado> produtos_reservados) {
        this.produtos_reservados = produtos_reservados;
    }

    public Reserva(List<ProdutoReservado> produtos_reservados, boolean ativo) {
        this.produtos_reservados = produtos_reservados;
        this.ativo = ativo;
    }

}
