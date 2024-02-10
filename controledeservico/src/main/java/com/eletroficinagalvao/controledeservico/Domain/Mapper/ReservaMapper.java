package com.eletroficinagalvao.controledeservico.Domain.Mapper;

import com.eletroficinagalvao.controledeservico.Domain.DTO.ProdutoDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.ReservarProdutoDTO;
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

    public Reserva criarReserva(List<ReservarProdutoDTO> produtos, int idOS){
        System.out.println(produtos);
        List<ProdutoReservado> produtosReservados = produtos.stream().map(e -> produtoMapper.reservar(e.uuidProduto(), e.quantidadeNescessaria())).toList();

        return reservaRepository.save(new Reserva(idOS, produtosReservados, true));
    }

}
