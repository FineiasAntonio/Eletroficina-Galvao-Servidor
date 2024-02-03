package com.eletroficinagalvao.controledeservico.Service;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import com.eletroficinagalvao.controledeservico.Exception.BadRequestException;
import com.eletroficinagalvao.controledeservico.Exception.NotFoundException;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoRepository;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoReservadoRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private ProdutoReservadoRepository reservadoRepository;

    @Autowired
    private EntityManager entityManager;

    public List<Produto> getAll() {
        return repository.findAll();
    }

    public Produto getById(String id) {
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
    public void update(String id, Produto produto) {

        Produto produtoSelecionado = getById(id);

        reservadoRepository.findById(id).ifPresent(e -> {
            BeanUtils.copyProperties(produto, e, "quantidade");
            reservadoRepository.save(e);
        });

        BeanUtils.copyProperties(produto, produtoSelecionado);

        repository.save(produtoSelecionado);
    }

    public void delete(String id) {

        reservadoRepository.findById(id).ifPresent(e -> {

            // TODO: preciso arrumar um jeito de apagar a reserva porém mantê-la para o usuario

            if(e.getQuantidadeNescessaria() != 0 && e.getQuantidadeReservada() != 0){
                log.error("Ainda há reserva para esse produto");
                throw new BadRequestException("Ainda há reservas para esse produto");
            }
        });

        repository.deleteById(id);
    }
}
