package com.eletroficinagalvao.controledeservico.Service;

import com.eletroficinagalvao.controledeservico.Domain.DTO.NotificationDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.*;
import com.eletroficinagalvao.controledeservico.Repository.OSRepository;
import com.eletroficinagalvao.controledeservico.Repository.ProdutoRepository;
import com.eletroficinagalvao.controledeservico.Repository.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private OSRepository osRepository;

    private static List<NotificationDTO> notificationPool = new LinkedList<>();

    public List<NotificationDTO> getNotificationPool() {
        return notificationPool;
    }

    @Scheduled (fixedDelay = 2, timeUnit = TimeUnit.HOURS)
    @Transactional
    public void verifyNewNotifications() {
        notificationPool.clear();

        BiPredicate<Produto, ProdutoReservado> verify = (x, t) -> x.getId().equals(t.getId_produto());

        Set<Produto> AllAvaiableProducts = produtoRepository.findByQuantidadeGreaterThan(0);
        List<OS> active = osRepository.findBySituacao(ServicoSituacao.AGUARDANDO_PECA);

        for (OS order : active) {
            Set<ProdutoReservado> CurrentOrderProducts = new HashSet<>(order.getIdReserva().getProdutos_reservados());

            Set<Produto> MatchedProducts = AllAvaiableProducts.stream()
                    .filter(e -> verify.test(e, CurrentOrderProducts.stream().findAny().get()))
                    .collect(Collectors.toSet());

            System.out.println(CurrentOrderProducts);
            System.out.println(MatchedProducts);

            notificationPool.addAll(
                    CurrentOrderProducts.stream()
                            .filter(expectedProduct ->
                                    MatchedProducts.stream()
                                            .anyMatch(storagedProduct ->
                                                    expectedProduct.getId_produto().equals(storagedProduct.getId()) &&
                                                            storagedProduct.getQuantidade() >= expectedProduct.getQuantidadeNescessaria()
                                            )
                            )
                            .map(expectedProduct -> NotificationDTO.builder()
                                    .uuid(expectedProduct.getId_produto())
                                    .orderID(order.getId())
                                    .produto(expectedProduct.getProduto())
                                    .quantidade(expectedProduct.getQuantidadeNescessaria())
                                    .build()
                            )
                            .toList()
            );

        }

        notificationPool.sort(Collections.reverseOrder(new Comparator<NotificationDTO>() {
            @Override
            public int compare(NotificationDTO o1, NotificationDTO o2) {
                OS RelatedOrder1 = osRepository.findById(o1.orderID()).get();
                OS RelatedOrder2 = osRepository.findById(o2.orderID()).get();
                return Double.compare(RelatedOrder1.getValorTotal(), RelatedOrder2.getValorTotal());
            }
        }));

    }
}
