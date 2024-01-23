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
    private ProdutoReservadoRepository produtoReservadoRepository;
    @Autowired
    private ProdutoMapper produtoMapper;
    @Autowired
    private ReservaRepository reservaRepository;

    public Reserva criarReserva(List<ProdutoDTO> produtos){
        List<ProdutoReservado> produtosReservados = produtos.stream().map(e -> produtoMapper.mapReserva(e)).toList();
        produtosReservados.stream().filter(e -> !produtoReservadoRepository.existsById(e.getId_produto())).forEach(e -> produtoReservadoRepository.save(e));
        produtosReservados.stream().forEach(e -> {
            if(produtoReservadoRepository.existsById(e.getId_produto())){
                produtoReservadoRepository.reservarExistente(e.getId_produto(), e.getQuantidadeNescessaria());
            }
        });
        return (Reserva) reservaRepository.save(new Reserva(produtosReservados, true));
    }

}
