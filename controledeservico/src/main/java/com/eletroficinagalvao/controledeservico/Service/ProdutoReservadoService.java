package com.eletroficinagalvao.controledeservico.Service;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import com.eletroficinagalvao.controledeservico.Exception.NotFoundException;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoRepository;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoReservadoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Qualifier ("ProdutoReservadoService")
@Log4j2
public class ProdutoReservadoService {

    @Autowired
    private ProdutoReservadoRepository repository;

    public List<ProdutoReservado> getAll() {
        return repository.findAll();
    }

    public ProdutoReservado getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
    }

    @Transactional
    public ProdutoReservado create(ProdutoReservado produto) {
        ProdutoReservado p = repository.save(produto);
        log.info("Produto registrado: " + produto.getProduto());
        return p;
    }

    @Transactional
    public void update(String id, ProdutoReservado produto) {
        ProdutoReservado produtoSelecionado = getById(id);
        produtoSelecionado = produto;
        repository.save(produtoSelecionado);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
