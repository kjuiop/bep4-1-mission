package com.back.shared.market.dto;

import com.back.boundedcontext.market.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@AllArgsConstructor
@Getter
public class OrderDto {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private long customerId;
    private String customerName;
    private int price;
    private int salePrice;
    private LocalDateTime requestPaymentDate;
    private LocalDateTime paymentDate;
    private LocalDateTime cancelDate;
    private LocalDateTime refundDate;

    public OrderDto(Order order) {
        this(
                order.getId(),
                order.getCreateDate(),
                order.getModifyDate(),
                order.getCustomer().getId(),
                order.getCustomer().getNickname(),
                order.getPrice(),
                order.getSalePrice(),
                order.getRequestPaymentDate(),
                order.getPaymentDate(),
                order.getCancelDate(),
                order.getRefundDate()
        );
    }
}
