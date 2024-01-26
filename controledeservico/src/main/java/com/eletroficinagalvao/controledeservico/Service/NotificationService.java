package com.eletroficinagalvao.controledeservico.Service;

import com.eletroficinagalvao.controledeservico.Domain.DTO.NotificationDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Reserva;
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

        BiPredicate<Produto, ProdutoReservado> verify = (x, t) -> x.getId_produto().equals(t.getId_produto());

        Set<Produto> AllAvaiableProducts = produtoRepository.listAvaiableItems();
        List<OS> active = osRepository.getWaitingOrders();

        for (OS order : active) {
            Set<ProdutoReservado> CurrentOrderProducts = new HashSet<>(order.getId_reserva().getProdutos_reservados());

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
                                                    expectedProduct.getId_produto().equals(storagedProduct.getId_produto()) &&
                                                            storagedProduct.getQuantidade() >= expectedProduct.getQuantidadeNescessaria()
                                            )
                            )
                            .map(expectedProduct -> NotificationDTO.builder()
                                    .uuid(expectedProduct.getId_produto())
                                    .orderID(order.getOs())
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
