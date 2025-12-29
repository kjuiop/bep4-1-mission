package com.back.boundedcontext.payout.app;

import com.back.shared.market.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutAddPayoutCandidateItemsUseCase {

    public void addPayoutCandidateItems(OrderDto order) {
        log.debug("addPayoutCandidateItems.order : {}", order.getId());
    }
}
