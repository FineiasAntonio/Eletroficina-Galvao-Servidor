package com.eletroficinagalvao.controledeservico.Domain.Mapper;

import com.eletroficinagalvao.controledeservico.Domain.DTO.Estoque.ProdutoDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.Reserva.ReservaProdutoRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Reserva;
import com.eletroficinagalvao.controledeservico.Repository.ReservaRepository;
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

    public Optional<Reserva> criarReserva(List<ReservaProdutoRequestDTO> produtos, List<ProdutoDTO> novosProdutos, int idOS) {

        List<ProdutoReservado> produtosReservados = new LinkedList<>();

        if (produtos != null && !produtos.isEmpty()) {
            produtosReservados.addAll(produtos.stream().map(e -> produtoMapper.reservar(e.uuidProduto(), e.quantidade())).toList());
        }
        if (novosProdutos != null && !novosProdutos.isEmpty()) {
            produtosReservados.addAll(novosProdutos.stream().map(e -> produtoMapper.mapReserva(e)).toList());
        }


        return produtosReservados.isEmpty() ? Optional.empty() : Optional.of(reservaRepository.save(new Reserva(idOS, produtosReservados, true)));

    }

    public Reserva atualizarReserva(Reserva reserva, List<ReservaProdutoRequestDTO> produtos, List<ProdutoDTO> novosProdutos){

        List<ProdutoReservado> produtosReservados = new LinkedList<>();

        if (produtos != null && !produtos.isEmpty()) {
            produtosReservados.addAll(produtos.stream().map(e -> produtoMapper.reservar(e.uuidProduto(), e.quantidade())).toList());
        }
        if (novosProdutos != null && !novosProdutos.isEmpty()) {
            produtosReservados.addAll(novosProdutos.stream().map(e -> produtoMapper.mapReserva(e)).toList());
        }

        reserva.getProdutos_reservados().addAll(produtosReservados);

        return reserva;
    }

}
