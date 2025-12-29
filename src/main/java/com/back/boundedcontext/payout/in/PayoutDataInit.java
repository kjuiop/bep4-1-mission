package com.back.boundedcontext.payout.in;

import com.back.boundedcontext.payout.app.PayoutFacade;
import com.back.boundedcontext.payout.domain.PayoutPolicy;
import com.back.standard.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
@Configuration
@Slf4j
public class PayoutDataInit {

    private final PayoutDataInit self;
    private final PayoutFacade payoutFacade;

    public PayoutDataInit(@Lazy PayoutDataInit self, PayoutFacade payoutFacade) {
        this.self = self;
        this.payoutFacade = payoutFacade;
    }

    @Bean
    @Order(4)
    public ApplicationRunner payoutDataInitApplicationRunner() {
        return args -> {
            self.forceMakePayoutReadyCandidatesItems();
            self.collectPayoutItemsMore();
        };
    }

    @Transactional
    public void forceMakePayoutReadyCandidatesItems() {
        payoutFacade.findPayoutCandidateItems().forEach(item -> {
            Utils.reflection.setField(
                    item,
                    "paymentDate",
                    LocalDateTime.now().minusDays(PayoutPolicy.getPayoutReadyWaitingDays() + 1)
            );
        });
    }

    @Transactional
    public void collectPayoutItemsMore() {
        payoutFacade.collectPayoutItemsMore(4);
        payoutFacade.collectPayoutItemsMore(2);
        payoutFacade.collectPayoutItemsMore(2);
    }

}
