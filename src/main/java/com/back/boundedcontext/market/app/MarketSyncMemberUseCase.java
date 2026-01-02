package com.back.boundedcontext.market.app;

import com.back.boundedcontext.cash.domain.CashMember;
import com.back.boundedcontext.market.domain.MarketMember;
import com.back.boundedcontext.market.out.MarketMemberRepository;
import com.back.global.eventpublisher.DomainEventPublisher;
import com.back.shared.job.dto.JobDto;
import com.back.shared.job.event.JobReadyInitEvent;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.member.event.MarketMemberCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class MarketSyncMemberUseCase {
    private final MarketMemberRepository marketMemberRepository;
    private final DomainEventPublisher eventPublisher;

    private boolean checkExecuteDataInit = true;

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

        if (checkExecuteDataInit && isReadyInitData()) {
            eventPublisher.publish(new JobReadyInitEvent(JobDto.readyByMarketMemberForMarket()));
            checkExecuteDataInit = false;
        }

        return _member;
    }

    private boolean isReadyInitData() {
        Optional<MarketMember> user1Member = marketMemberRepository.findByUsername("user1");
        Optional<MarketMember> user2Member = marketMemberRepository.findByUsername("user2");
        Optional<MarketMember> user3Member = marketMemberRepository.findByUsername("user3");

        return user1Member.isPresent() && user2Member.isPresent() && user3Member.isPresent();
    }
}
