package com.back.boundedcontext.payout.app;

import com.back.boundedcontext.payout.domain.Payout;
import com.back.boundedcontext.payout.domain.PayoutMember;
import com.back.boundedcontext.payout.out.PayoutMemberRepository;
import com.back.boundedcontext.payout.out.PayoutRepository;
import com.back.shared.payout.dto.PayoutMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
@Service
@RequiredArgsConstructor
public class PayoutCreatePayoutUseCase {

    private final PayoutRepository payoutRepository;
    private final PayoutMemberRepository payoutMemberRepository;

    public Payout createPayout(PayoutMemberDto payee) {
        PayoutMember _payee = payoutMemberRepository.getReferenceById(payee.getId());
        Payout payout = payoutRepository.save(new Payout(_payee));
        return payout;
    }
}
