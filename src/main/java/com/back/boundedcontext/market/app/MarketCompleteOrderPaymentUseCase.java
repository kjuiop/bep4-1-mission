package com.back.boundedcontext.market.app;

import com.back.boundedcontext.market.domain.Order;
import com.back.boundedcontext.market.out.OrderRepository;
import com.back.shared.cash.event.CashOrderPaymentSuccessdedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class MarketCompleteOrderPaymentUseCase {

    private final OrderRepository orderRepository;

    public void handle(CashOrderPaymentSuccessdedEvent event) {
        Order order = orderRepository.findById(event.getOrder().getId()).get();
        order.completePayment();
    }
}
