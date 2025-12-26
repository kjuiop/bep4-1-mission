package com.back.boundedcontext.market.out;

import com.back.boundedcontext.market.domain.MarketMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
public interface MarketMemberRepository extends JpaRepository<MarketMember, Long> {
    Optional<MarketMember> findByUsername(String username);
}
