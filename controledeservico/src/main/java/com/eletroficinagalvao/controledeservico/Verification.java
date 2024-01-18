package com.eletroficinagalvao.controledeservico;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Reserva;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoRepository;
import com.eletroficinagalvao.controledeservico.Repository.ReservaRepository;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Verification {

    private ProdutoRepository produtoRepository;
    private ReservaRepository reservaRepository;

    public void fodase() {

        List<Produto> avaiable = produtoRepository.listAvaiableItems();
        List<Reserva> active = reservaRepository.listReservasAtivas();

        for (Produto produtoEstocado : avaiable) {
            ProdutoReservado produtoReservadoCorrespondente = active.stream()
                    .flatMap(x -> x.getProdutos_reservados().stream())
                    .filter(x -> x.getId_produto().equals(produtoEstocado.getId_produto()))
                    .findFirst().get();

            int qntEstocado = produtoEstocado.getQuantidade();
            int qntNescessaria = produtoReservadoCorrespondente.getQuantidadeNescessaria();
            int qntReservada = produtoReservadoCorrespondente.getQuantidadeReservada();

            while (qntEstocado != 0 && qntReservada < qntNescessaria){
                qntEstocado--;
                qntReservada++;
            }

            produtoEstocado.setQuantidade(qntEstocado);
            produtoReservadoCorrespondente.setQuantidadeReservada(qntReservada);

        }

    }

}
