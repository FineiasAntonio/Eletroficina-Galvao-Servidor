package com.eletroficinagalvao.controledeservico.Service;

import com.eletroficinagalvao.controledeservico.Domain.DTO.ReservaRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Reserva;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import com.eletroficinagalvao.controledeservico.Exception.BadRequestException;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoRepository;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoReservadoRepository;
import com.eletroficinagalvao.controledeservico.Repository.ReservaRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

    @Autowired
    private ProdutoRepository estoqueRepository;
    @Autowired
    private ProdutoReservadoRepository reservadoRepository;
    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> getAll(){
        return reservaRepository.findAll();
    }

    @Transactional
    public void reservar(ReservaRequestDTO dto){
        if (dto.uuid().trim().isEmpty() || dto.quantidade().trim().isEmpty())
            throw new BadRequestException("DTO Inválido");

        Produto p1 = estoqueRepository.findById(dto.uuid()).get();
        ProdutoReservado p2 = reservadoRepository.findById(dto.uuid()).get();

        p1.setQuantidade(p1.getQuantidade() - Integer.parseInt(dto.quantidade()));
        p2.setQuantidadeReservada(p2.getQuantidadeReservada() + Integer.parseInt(dto.quantidade()));

        estoqueRepository.save(p1);
        reservadoRepository.save(p2);

    }

}
