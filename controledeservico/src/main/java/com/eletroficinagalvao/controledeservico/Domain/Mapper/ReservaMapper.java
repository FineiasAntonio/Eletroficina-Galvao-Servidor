package com.eletroficinagalvao.controledeservico.Domain.Mapper;

import com.eletroficinagalvao.controledeservico.Domain.DTO.ProdutoDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Reserva;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoReservadoRepository;
import com.eletroficinagalvao.controledeservico.Repository.ReservaRepository;
import com.eletroficinagalvao.controledeservico.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservaMapper {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private ProdutoReservadoRepository produtoReservadoRepository;
    @Autowired
    private ProdutoMapper produtoMapper;
    @Autowired
    private ReservaRepository reservaRepository;

    public Reserva criarReserva(List<ProdutoDTO> produtos){
        List<ProdutoReservado> produtosReservados = produtos.stream().map(e -> produtoMapper.mapReserva(e)).toList();

        return (Reserva) reservaRepository.save(Reserva.builder()
                                                        .produtos_reservados(produtosReservados)
                                                        .build());

    }

}
