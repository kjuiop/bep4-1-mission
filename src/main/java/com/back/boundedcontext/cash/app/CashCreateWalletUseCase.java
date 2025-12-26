package com.back.boundedcontext.cash.app;

import com.back.boundedcontext.cash.domain.CashMember;
import com.back.boundedcontext.cash.domain.Wallet;
import com.back.boundedcontext.cash.out.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class CashCreateWalletUseCase {

    private final WalletRepository walletRepository;

    public Wallet createWallet(CashMember member) {
        Wallet wallet = new Wallet(member);
        return walletRepository.save(wallet);
    }
}
