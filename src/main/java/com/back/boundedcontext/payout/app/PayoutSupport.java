package com.back.boundedcontext.payout.app;

import com.back.boundedcontext.payout.domain.PayoutMember;
import com.back.boundedcontext.payout.out.PayoutMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
@Service
@RequiredArgsConstructor
public class PayoutSupport {

    private final PayoutMemberRepository payoutMemberRepository;

    public Optional<PayoutMember> findHoldingMember() {
        return payoutMemberRepository.findByUsername("holding");
    }

    public Optional<PayoutMember> findMemberById(long id) {
        return payoutMemberRepository.findById(id);
    }
}
