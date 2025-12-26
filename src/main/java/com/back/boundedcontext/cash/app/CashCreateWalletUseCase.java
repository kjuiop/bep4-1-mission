package com.back.boundedcontext.cash.app;

import com.back.boundedcontext.cash.domain.CashMember;
import com.back.boundedcontext.cash.domain.Wallet;
import com.back.boundedcontext.cash.out.CashMemberRepository;
import com.back.boundedcontext.cash.out.WalletRepository;
import com.back.shared.member.dto.CashMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class CashCreateWalletUseCase {

    private final CashMemberRepository cashMemberRepository;
    private final WalletRepository walletRepository;

    public Wallet createWallet(CashMemberDto member) {
        CashMember _member = cashMemberRepository.getReferenceById(member.getId());
        Wallet wallet = new Wallet(_member);
        return walletRepository.save(wallet);
    }
}
