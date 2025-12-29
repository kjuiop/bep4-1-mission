package com.back.boundedcontext.market.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.market.event.MarketOrderPaymentCompletedEvent;
import com.back.shared.market.event.MarketOrderRequestPaymentStartedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Entity
@Table(name = "MARKET_ORDER")
@NoArgsConstructor
@Getter
public class Order extends BaseIdAndTime {

    @ManyToOne(fetch = LAZY)
    private MarketMember buyer;

    private LocalDateTime requestPaymentDate;

    private LocalDateTime paymentDate;

    private LocalDateTime cancelDate;

    private LocalDateTime refundDate;

    private int price;

    private int salePrice;

    @OneToMany(mappedBy = "order", cascade = {PERSIST, MERGE, REMOVE}, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Order(Cart cart) {
        this.buyer = cart.getCustomer();

        cart.getItems().forEach(item -> {
            addItem(item.getProduct());
        });
    }

    public void addItem(Product product) {
        OrderItem orderItem = new OrderItem(
                this,
                product,
                product.getName(),
                product.getPrice(),
                product.getSalePrice()
        );

        items.add(orderItem);
        price += product.getPrice();
        salePrice += product.getSalePrice();
    }

    public void completePayment() {
        paymentDate = LocalDateTime.now();

        publishEvent(
                new MarketOrderPaymentCompletedEvent(
                        toDto()
                )
        );
    }

    public boolean isPaid() {
        return paymentDate != null;
    }

    public boolean isCanceled() {
        return cancelDate != null;
    }

    public boolean isPaymentInProgress() {
        return requestPaymentDate != null && paymentDate == null && cancelDate == null;
    }

    public void requestPayment(long pgPaymentAmount) {
        markAsRequestPaymentStarted();

        publishEvent(
                new MarketOrderRequestPaymentStartedEvent(
                        toDto(),
                        pgPaymentAmount
                )
        );
    }

    private void markAsRequestPaymentStarted() {
        requestPaymentDate = LocalDateTime.now();
    }

    public void cancelRequestPayment() {
        requestPaymentDate = null;
    }

    public void refund() {
        requestPaymentDate = null;
    }

    public OrderDto toDto() {
        return new OrderDto(
                getId(),
                getCreateDate(),
                getModifyDate(),
                buyer.getId(),
                buyer.getNickname(),
                price,
                salePrice,
                requestPaymentDate,
                paymentDate,
                cancelDate,
                refundDate
        );
    }
}

