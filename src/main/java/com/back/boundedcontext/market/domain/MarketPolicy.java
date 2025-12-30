package com.back.boundedcontext.market.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Component
public class MarketPolicy {
    public static double PRODUCT_PAYOUT_RATE;

    @Value("${custom.market.product.payoutRate}")
    public void setProductPayoutRate(double rate) {
        PRODUCT_PAYOUT_RATE = rate;
    }

    public static long calculatePayoutFee(long salePrice, double payoutRate) {
        return salePrice - calculateSalePriceWithoutFee(salePrice, payoutRate);
    }

    public static long calculateSalePriceWithoutFee(long salePrice, double payoutRate) {
        return Math.round(salePrice * payoutRate / 100);
    }
}
