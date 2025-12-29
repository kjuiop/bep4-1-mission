package com.back.boundedcontext.payout.out;

import com.back.boundedcontext.payout.domain.PayoutMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
public interface PayoutMemberRepository extends JpaRepository<PayoutMember, Long> {
    Optional<PayoutMember> findByUsername(String username);
}
