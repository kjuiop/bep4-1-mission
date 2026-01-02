package com.back.boundedcontext.market.app;

import com.back.boundedcontext.cash.domain.CashMember;
import com.back.boundedcontext.market.domain.Order;
import com.back.boundedcontext.market.out.OrderRepository;
import com.back.global.eventpublisher.DomainEventPublisher;
import com.back.shared.cash.event.CashOrderPaymentSuccessdedEvent;
import com.back.shared.job.dto.JobDto;
import com.back.shared.job.event.JobReadyInitEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class MarketCompleteOrderPaymentUseCase {

    private final OrderRepository orderRepository;
    private final DomainEventPublisher eventPublisher;

    private boolean checkExecuteDataInit = true;


    public void handle(CashOrderPaymentSuccessdedEvent event) {
        Order order = orderRepository.findById(event.getOrder().getId()).get();
        order.completePayment();

        if (checkExecuteDataInit) {
            eventPublisher.publish(new JobReadyInitEvent(JobDto.readyByPayout()));
            checkExecuteDataInit = false;
        }
    }
}
