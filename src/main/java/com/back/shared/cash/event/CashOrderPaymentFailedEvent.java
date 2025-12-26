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
public class CashOrderPaymentFailedEvent {

    private final String resultCode;
    private final String msg;
    private final OrderDto order;
    private final long pgPaymentAmount;
    private final long shortFailAmount;
}
