package com.back.shared.cash.event;

import com.back.shared.market.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@AllArgsConstructor
@Getter
public class CashOrderPaymentSuccessdedEvent {
    private final OrderDto order;
    private final long pgPaymentAmount;
}
