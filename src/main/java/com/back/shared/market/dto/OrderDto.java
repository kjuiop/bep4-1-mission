package com.back.shared.market.dto;

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
}
