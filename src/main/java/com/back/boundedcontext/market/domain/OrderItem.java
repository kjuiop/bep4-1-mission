package com.back.boundedcontext.market.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Entity
@Table(name = "MARKET_ORDER_ITEM")
@NoArgsConstructor
@Getter
public class OrderItem extends BaseIdAndTime {

    @ManyToOne(fetch = LAZY)
    private Order order;

    @ManyToOne(fetch = LAZY)
    private Product product;

    private String productName;

    private int price;

    private int salePrice;

    private int payoutRate = MarketPolicy.PRODUCT_PAYOUT_RATE;

    public OrderItem(Order order, Product product, String productName, int price, int salePrice) {
        this.order = order;
        this.product = product;
        this.productName = productName;
        this.price = price;
        this.salePrice = salePrice;
    }

}
