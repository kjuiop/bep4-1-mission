package com.back.boundedcontext.cash.app;

import com.back.boundedcontext.cash.domain.CashMember;
import com.back.boundedcontext.cash.domain.Wallet;
import com.back.boundedcontext.cash.out.CashMemberRepository;
import com.back.boundedcontext.cash.out.WalletRepository;
import com.back.boundedcontext.post.domain.PostMember;
import com.back.global.eventpublisher.DomainEventPublisher;
import com.back.shared.cash.event.CashReadyInitEvent;
import com.back.shared.member.dto.CashMemberDto;
import com.back.shared.post.event.PostReadyInitEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class CashCreateWalletUseCase {

    private final CashMemberRepository cashMemberRepository;
    private final WalletRepository walletRepository;
    private final DomainEventPublisher eventPublisher;

    private boolean checkExecuteDataInit = true;

    public Wallet createWallet(CashMemberDto member) {
        // id 만 필요할 때에는 referenceId 를 조회만해서 데이터베이스에서 select 를 안할 수 있다.
        // 이렇게하면 SELECT 쿼리가 실행되지 않음 (프록시 객체 생성, ID 값만으로 프록시 객체 생성)
        CashMember _member = cashMemberRepository.getReferenceById(member.getId());
        // 만일 String name = _member.getName(); 이라는 함수가 호출되면 이 순간 쿼리가 날라감
        Wallet wallet = new Wallet(_member);
        Wallet saved =  walletRepository.save(wallet);

        // 서버가 1대일 때 사용, 2대 이상일 때에는 DB 에 상태 저장 필요
//        if (checkExecuteDataInit && isReadyInitData()) {
//            eventPublisher.publish(new CashReadyInitEvent());
//            // 수행 완료
//            checkExecuteDataInit = false;
//        }

        return saved;
    }

    private boolean isReadyInitData() {
        CashMember user1Member = cashMemberRepository.findByUsername("user1").get();
        CashMember user2Member = cashMemberRepository.findByUsername("user2").get();
        CashMember user3Member = cashMemberRepository.findByUsername("user3").get();

        Optional<Wallet> user1Wallet = walletRepository.findByHolder(user1Member);
        Optional<Wallet> user2Wallet = walletRepository.findByHolder(user2Member);
        Optional<Wallet> user3Wallet = walletRepository.findByHolder(user3Member);

        return user1Wallet.isPresent() && user2Wallet.isPresent() && user3Wallet.isPresent();
    }
}
