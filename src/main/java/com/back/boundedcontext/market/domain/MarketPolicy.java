package com.back.boundedcontext.market.domain;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
public class MarketPolicy {
    public static double PRODUCT_PAYOUT_RATE;

    @Value("${customer.market.product.payoutRate}")
    public void setProductPayoutRate(double rate) {
        PRODUCT_PAYOUT_RATE = rate;
    }
}
