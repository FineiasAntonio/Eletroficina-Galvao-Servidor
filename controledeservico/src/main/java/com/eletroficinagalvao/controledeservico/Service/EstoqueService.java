package com.eletroficinagalvao.controledeservico.Service;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Exception.NotFoundException;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Qualifier ("EstoqueService")
@Log4j2
public class EstoqueService {

    @Autowired
    private ProdutoRepository repository;

    public List<Produto> getAll() {
        return repository.findAll();
    }

    public Produto getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
    }

    public List<Produto> getByLikeThisName(String name) {
        return repository.findByProdutoLike(name);
    }

    @Transactional
    public void create(Produto produto) {
        // trocar o DTO aqui como parametro

        // TODO: fazer a correlação com produto reservado existente para o método delete funcionar perfeitamente

        Produto p = repository.save(produto);

        log.info("Produto registrado: " + produto.getProduto());
    }

    @Transactional
    public void update(UUID id, Produto produto) {

        Produto produtoSelecionado = getById(id);

        BeanUtils.copyProperties(produto, produtoSelecionado);

        repository.save(produtoSelecionado);
    }

    public void delete(String id) {

        repository.deleteById(UUID.fromString(id));
    }
}
