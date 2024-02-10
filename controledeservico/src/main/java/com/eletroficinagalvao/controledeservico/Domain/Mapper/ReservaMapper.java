package com.eletroficinagalvao.controledeservico.Domain.Mapper;

import com.eletroficinagalvao.controledeservico.Domain.DTO.ProdutoDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Reserva;
import com.eletroficinagalvao.controledeservico.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservaMapper {

    @Autowired
    private ProdutoMapper produtoMapper;
    @Autowired
    private ReservaRepository reservaRepository;

    public Reserva criarReserva(List<ProdutoDTO> produtos, int idOS){
        List<ProdutoReservado> produtosReservados = produtos.stream().map(e -> produtoMapper.mapReserva(e)).toList();

        return reservaRepository.save(new Reserva(idOS, produtosReservados, true));

//        return (Reserva) reservaRepository.save(Reserva.builder()
//                .produtos_reservados(produtosReservados)
//                .ativo(true)
//                .idOS(idOS)
//                .build()
//        );
    }

}
