package com.back.boundedcontext.market.domain;

import com.back.global.jpa.entity.BaseManualIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Entity
@Table(name = "MARKET_CART")
@NoArgsConstructor
@Getter
public class Cart extends BaseManualIdAndTime {

    @ManyToOne(fetch = FetchType.LAZY)
    private MarketMember customer;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public Cart(MarketMember customer) {
        super(customer.getId());
        this.customer = customer;
    }

    public boolean hasItems() {
        return !this.getItems().isEmpty();
    }

    public void addItem(Product product) {
        CartItem cartItem = new CartItem(this, product);
        this.getItems().add(cartItem);
    }

    public void clearItems() {
        this.getItems().clear();
    }
}
