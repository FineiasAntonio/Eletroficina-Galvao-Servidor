package com.eletroficinagalvao.controledeservico.Service;

import com.eletroficinagalvao.controledeservico.Domain.DTO.Reserva.ReservaProdutoRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Reserva;
import com.eletroficinagalvao.controledeservico.Exception.BadRequestException;
import com.eletroficinagalvao.controledeservico.Repository.OSRepository;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoRepository;
import com.eletroficinagalvao.controledeservico.Repository.ReservaRepository;

import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private OSRepository osRepository;

    public List<Reserva> getAll() {
        return reservaRepository.findAll();
    }

    @Transactional
    public void reservarProdutoDoEstoque(int id_os, ReservaProdutoRequestDTO produto) {

        //Segunda verificação pra ver se há a quantidade no estoque
        Produto produtoDoEstoque = produtoRepository.findById(produto.uuidProduto()).get();
        if (produtoDoEstoque.getQuantidade() < produto.quantidade()) {
            throw new BadRequestException("Não há quantidade suficiente de %s para ser reservado".formatted(produtoDoEstoque.getProduto()));
        } else {
            produtoDoEstoque.setQuantidade(produtoDoEstoque.getQuantidade() - produto.quantidade());
            produtoRepository.save(produtoDoEstoque);
            log.info("Produto do estoque reduzido");
        }

        Reserva reserva = reservaRepository.findByIdOS(id_os);
        for (ProdutoReservado e: reserva.getProdutos_reservados()){
            if (e.getId().equals(produto.uuidProduto())){
                e.setQuantidade(e.getQuantidade() + produto.quantidade());
            }
        }

        OS os = osRepository.findById(id_os).get();
        os.setReserva(reserva);
        osRepository.save(os);
        //TODO: procurar um jeito de fazer uma referência entre as collections

        reservaRepository.save(reserva);
        log.info("Reserva salva");
    }

}
