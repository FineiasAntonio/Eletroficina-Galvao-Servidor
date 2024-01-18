package com.eletroficinagalvao.controledeservico.Service;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Exception.NotFoundException;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoRepository;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Qualifier ("ProdutoService")
@Log4j2
public class ProdutoService{

    @Autowired
    private ProdutoRepository repository;

    public List<Produto> getAll() {
        return repository.findAll();
    }

    public Produto getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
    }

    public List<Produto> getByLikeThisName(String name) {
        return repository.getByLikeThisName(name);
    }

    @Transactional
    public Produto create(Produto produto) {
        // trocar o DTO aqui como parametro
        Produto p = repository.save(produto);
        log.info("Produto registrado: " + produto.getProduto());
        return p;
    }

    @Transactional
    public void update(String id, Produto produto) {
        Produto produtoSelecionado = getById(id);
        produtoSelecionado = produto;
        repository.save(produtoSelecionado);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
