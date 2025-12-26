package com.back.boundedcontext.market.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Entity
@Table(name = "MARKET_PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Product extends BaseIdAndTime {

    private String sourceTypeCode;

    private long sourceId;

    private String name;

    private String description;

    private int price;

    private int salePrice;

    @ManyToOne(fetch = LAZY)
    private MarketMember seller;
}
