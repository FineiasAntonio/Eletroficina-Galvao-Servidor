package com.eletroficinagalvao.controledeservico.Service;

import com.eletroficinagalvao.controledeservico.Domain.DTO.Reserva.ReservaProdutoExistenteDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Reserva;
import com.eletroficinagalvao.controledeservico.Exception.BadRequestException;
import com.eletroficinagalvao.controledeservico.Exception.NotFoundException;
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

    public List<Reserva> getAll() {
        return reservaRepository.findAll();
    }

    @Transactional
    public void reservarProdutoDoEstoque(int id_os, ReservaProdutoExistenteDTO produto) {

        Reserva reserva = reservaRepository.findByIdOS(id_os);

        if (!reserva.isAtivo())
            throw new BadRequestException("Reserva fechada");

        //verificação pra ver se há a quantidade no estoque
        Produto produtoDoEstoque = produtoRepository.findById(produto.uuidProduto()).orElseThrow(() -> new NotFoundException("Produto não encontrado no estoque"));
        if (produtoDoEstoque.getQuantidade() < produto.quantidade()) {

            throw new BadRequestException("Não há quantidade suficiente de %s para ser reservado".formatted(produtoDoEstoque.getProduto()));

        } else if (reserva.getProdutos_reservados().stream()
                .anyMatch(x -> (x.getId().equals(produto.uuidProduto()) && x.getQuantidade() + produto.quantidade() > x.getQuantidadeNescessaria()))) {

            throw new BadRequestException("Você está tentando reservar mais do que é nescessário");
        } else {
            produtoDoEstoque.setQuantidade(produtoDoEstoque.getQuantidade() - produto.quantidade());
            produtoRepository.save(produtoDoEstoque);
            log.info("Produto do estoque reduzido");
        }


        for (ProdutoReservado e : reserva.getProdutos_reservados()) {
            if (e.getId().equals(produto.uuidProduto())) {
                e.setQuantidade(e.getQuantidade() + produto.quantidade());
            }
        }

        reservaRepository.save(reserva);
        log.info("Reserva salva");
    }

}
