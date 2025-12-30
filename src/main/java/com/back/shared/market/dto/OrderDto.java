package com.back.shared.market.dto;

import com.back.standard.modeltype.CanGetModelTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@AllArgsConstructor
@Getter
public class OrderDto implements CanGetModelTypeCode {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private long buyerId;
    private String buyerName;
    private int price;
    private int salePrice;
    private LocalDateTime requestPaymentDate;
    private LocalDateTime paymentDate;
    private LocalDateTime cancelDate;
    private LocalDateTime refundDate;

    @Override
    public String getModelTypeCode() {
        return "Order";
    }
}
