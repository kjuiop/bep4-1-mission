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
        // id 만 필요할 때에는 referenceId 를 조회만해서 데이터베이스에서 select 를 안할 수 있다.
        // 이렇게하면 SELECT 쿼리가 실행되지 않음 (프록시 객체 생성, ID 값만으로 프록시 객체 생성)
        CashMember _member = cashMemberRepository.getReferenceById(member.getId());
        // 만일 String name = _member.getName(); 이라는 함수가 호출되면 이 순간 쿼리가 날라감
        Wallet wallet = new Wallet(_member);
        return walletRepository.save(wallet);
    }
}
