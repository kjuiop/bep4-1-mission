package com.back.boundedcontext.payout.out;

import com.back.boundedcontext.payout.domain.PayoutCandidateItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
public interface PayoutCandidateItemRepository extends JpaRepository<PayoutCandidateItem, Long> {
    List<PayoutCandidateItem> findByPayoutItemIsNullAndPaymentDateBeforeOrderByPayeeAscIdAsc(LocalDateTime paymentDateDaysAgo, Pageable pageable);
}
