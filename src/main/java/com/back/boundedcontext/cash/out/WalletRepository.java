package com.back.boundedcontext.cash.out;

import com.back.boundedcontext.cash.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
