package com.back.boundedcontext.cash.out;

import com.back.boundedcontext.cash.domain.CashMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
public interface CashMemberRepository extends JpaRepository<CashMember, Long> {
    Optional<CashMember> findByUsername(String username);
}
