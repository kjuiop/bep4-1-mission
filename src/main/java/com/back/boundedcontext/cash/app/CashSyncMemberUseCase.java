package com.back.boundedcontext.cash.app;

import com.back.boundedcontext.cash.domain.CashMember;
import com.back.boundedcontext.cash.out.CashMemberRepository;
import com.back.global.eventpublisher.EventPublisher;
import com.back.shared.member.dto.CashMemberDto;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.member.event.CashMemberCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class CashSyncMemberUseCase {

    private final CashMemberRepository cashMemberRepository;
    private final EventPublisher eventPublisher;

    public CashMember syncMember(MemberDto member) {
        boolean isNew = !cashMemberRepository.existsById(member.getId());

        CashMember _member = cashMemberRepository.save(
                new CashMember(
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
                    new CashMemberCreatedEvent(
                            new CashMemberDto(_member)
                    )
            );
        }
        return _member;
    }
}
