package com.back.boundedcontext.market.app;

import com.back.boundedcontext.market.domain.Order;
import com.back.boundedcontext.market.out.OrderRepository;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class MarketCancelOrderRequestPaymentUseCase {
    private final OrderRepository orderRepository;

    public void handle(CashOrderPaymentFailedEvent event) {
        Order order = orderRepository.findById(event.getOrder().getId()).get();
        order.cancelRequestPayment();;
    }
}
