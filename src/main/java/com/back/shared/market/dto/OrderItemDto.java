package com.back.shared.market.dto;

import com.back.standard.modeltype.CanGetModelTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
@AllArgsConstructor
@Getter
public class OrderItemDto implements CanGetModelTypeCode {

    private final long id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final long orderId;
    private final long buyerId;
    private final String buyerName;
    private final long sellerId;
    private final String sellerName;
    private final long productId;
    private final String productName;
    private final int price;
    private final int salePrice;
    private final double payoutRate;
    private final long payoutFee;
    private final long salePriceWithoutFee;

    @Override
    public String getModelTypeCode() {
        return "OrderItem";
    }
}
