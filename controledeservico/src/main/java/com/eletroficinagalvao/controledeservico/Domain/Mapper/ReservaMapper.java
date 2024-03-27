package com.eletroficinagalvao.controledeservico.Domain.Mapper;

import com.eletroficinagalvao.controledeservico.Domain.DTO.Estoque.ProdutoDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.Reserva.ReservaDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.Reserva.ReservaProdutoExistenteDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Reserva;
import com.eletroficinagalvao.controledeservico.Repository.ReservaRepository;
import com.eletroficinagalvao.controledeservico.Service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class ReservaMapper {

    @Autowired
    private ProdutoMapper produtoMapper;
    @Autowired
    private ReservaRepository reservaRepository;

    public Optional<Reserva> criarReserva(ReservaDTO reserva, int idOS) {

        if(reserva.produtosExistentes().isEmpty() && reserva.produtosNovos().isEmpty()){
            Reserva reservaVazia = new Reserva(idOS, new LinkedList<>(), false, reserva.maoDeObra());
            return Optional.of(reservaRepository.save(reservaVazia));
        }

        List<ProdutoReservado> produtosReservados = new LinkedList<>();

        if (reserva.produtosExistentes() != null && !reserva.produtosExistentes().isEmpty()) {
            produtosReservados.addAll(reserva.produtosExistentes().stream().map(e -> produtoMapper.reservar(e.uuidProduto(), e.quantidade())).toList());
        }
        if (reserva.produtosNovos() != null && !reserva.produtosNovos().isEmpty()) {
            produtosReservados.addAll(reserva.produtosNovos().stream().map(e -> produtoMapper.mapReserva(e)).toList());
        }

        return produtosReservados.isEmpty() ? Optional.empty() : Optional.of(reservaRepository.save(
                new Reserva(idOS, produtosReservados, true, reserva.maoDeObra()))
        );

    }

    public Reserva atualizarReserva(Reserva reserva, ReservaDTO reservaAtualizada){

        reserva = reservaRepository.findById(reserva.getId()).orElseThrow(() -> new NullPointerException("Não era pra ser null"));

        List<ProdutoReservado> produtosReservados = new LinkedList<>();

        if (reservaAtualizada.produtosExistentes() != null && !reservaAtualizada.produtosExistentes().isEmpty()) {
            produtosReservados = (reservaAtualizada.produtosExistentes().stream().map(e -> produtoMapper.reservar(e.uuidProduto(), e.quantidade())).toList());
        }
        if (reservaAtualizada.produtosNovos() != null && !reservaAtualizada.produtosNovos().isEmpty()) {
            produtosReservados = (reservaAtualizada.produtosNovos().stream().map(e -> produtoMapper.mapReserva(e)).toList());
        }


        reserva.getProdutos_reservados().addAll(produtosReservados);
        reserva.setMaoDeObra(reservaAtualizada.maoDeObra());

        if(!reserva.getProdutos_reservados().isEmpty()){
            reserva.setAtivo(true);
        }

        return reservaRepository.save(reserva);
    }

}
