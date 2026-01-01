package com.back.boundedcontext.payout.app;

import com.back.boundedcontext.payout.domain.PayoutMember;
import com.back.boundedcontext.payout.out.PayoutMemberRepository;
import com.back.global.eventpublisher.DomainEventPublisher;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.payout.event.PayoutMemberCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
@Service
@RequiredArgsConstructor
public class PayoutSyncMemberUseCase {

    private final PayoutMemberRepository payoutMemberRepository;
    private final DomainEventPublisher eventPublisher;

    public PayoutMember syncMember(MemberDto member) {
        boolean isNew = !payoutMemberRepository.existsById(member.getId());

        PayoutMember _member = payoutMemberRepository.save(
                new PayoutMember(
                        member.getId(),
                        member.getCreateDate(),
                        member.getModifyDate(),
                        member.getUsername(),
                        "",
                        member.getNickname(),
                        member.getActivityScore()
                )
        );

        if (isNew) {
            eventPublisher.publish(
                    new PayoutMemberCreatedEvent(
                            _member.toDto()
                    )
            );
        }

        return _member;
    }
}
