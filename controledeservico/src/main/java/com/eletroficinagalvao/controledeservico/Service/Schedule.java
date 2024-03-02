package com.eletroficinagalvao.controledeservico.Service;

import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ServicoSituacao;
import com.eletroficinagalvao.controledeservico.Domain.Entity.SubSituacao;
import com.eletroficinagalvao.controledeservico.Repository.OSRepository;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Log4j2
public class Schedule {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private OSRepository osRepository;

    //@Scheduled
    private void atualizarValoresProdutos() {
        // Se algum atributo de algum produto do estoque for alterado esse serviço vai atualizar os produtos reservados nas OS

        for (OS e : osRepository.findAll()) {

            if (e.getReserva() == null)
                continue;

            e.getReserva().getProdutos_reservados().stream()
                    .forEach(x -> {
                        Produto produto = produtoRepository.findById(x.getId()).get();
                        BeanUtils.copyProperties(produto, x, "quantidade", "quantidadeNescessaria");
                    });
            osRepository.save(e);
        }

    }

    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.MINUTES)
    private void atualizarSituacaoOrdem() {
        // Se alguma ordem de serviço já possuir todas as peças nescessarias reservadas esse serviço vai atualizar o status para "EM ANDAMENTO"

        osRepository.findBySituacao(ServicoSituacao.AGUARDANDO_PECA).stream()
                .filter(x -> x.getReserva().getProdutos_reservados().stream()
                        .allMatch(e -> e.getQuantidade() == e.getQuantidadeNescessaria()))
                .forEach(x -> {

                    x.setSituacao(ServicoSituacao.EM_ANDAMENTO);
                    x.setSubSituacao(SubSituacao.APROVADO);
                    x.getReserva().setAtivo(false);
                    osRepository.save(x);
                });

    }

}
