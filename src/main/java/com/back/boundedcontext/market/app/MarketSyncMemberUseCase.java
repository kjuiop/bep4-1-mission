package com.back.boundedcontext.market.app;

import com.back.boundedcontext.market.domain.MarketMember;
import com.back.boundedcontext.market.out.MarketMemberRepository;
import com.back.global.eventpublisher.EventPublisher;
import com.back.shared.member.dto.MarketMemberDto;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.member.event.MarketMemberCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class MarketSyncMemberUseCase {
    private final MarketMemberRepository marketMemberRepository;
    private final EventPublisher eventPublisher;

    public MarketMember syncMember(MemberDto member) {
        boolean isNew = !marketMemberRepository.existsById(member.getId());

        MarketMember _member = marketMemberRepository.save(
                new MarketMember(
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
                    new MarketMemberCreatedEvent(
                            _member.toDto()
                    )
            );
        }

        return _member;
    }
}
