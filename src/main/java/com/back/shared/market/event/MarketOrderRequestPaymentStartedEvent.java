package com.back.shared.market.event;

import com.back.shared.market.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@AllArgsConstructor
@Getter
public class MarketOrderRequestPaymentStartedEvent {
    private OrderDto order;
    private long pgPaymentAmount;
}
