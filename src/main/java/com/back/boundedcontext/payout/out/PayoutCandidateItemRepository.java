package com.back.boundedcontext.payout.out;

import com.back.boundedcontext.payout.domain.PayoutCandidateItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
public interface PayoutCandidateItemRepository extends JpaRepository<PayoutCandidateItem, Long> {
}
