package com.back.boundedcontext.cash.out;

import com.back.boundedcontext.cash.domain.CashMember;
import com.back.boundedcontext.cash.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByHolder(CashMember holder);
}
