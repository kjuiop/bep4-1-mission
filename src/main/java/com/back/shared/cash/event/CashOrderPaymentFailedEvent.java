package com.back.shared.cash.event;

import com.back.shared.market.dto.OrderDto;
import com.back.standard.resulttype.ResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@AllArgsConstructor
@Getter
public class CashOrderPaymentFailedEvent implements ResultType {

    private final String resultCode;
    private final String msg;
    private final OrderDto order;
    private final long pgPaymentAmount;
    private final long shortFailAmount;
}
