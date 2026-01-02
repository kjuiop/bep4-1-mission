package com.back.boundedcontext.cash.app;

import com.back.boundedcontext.cash.domain.CashMember;
import com.back.boundedcontext.cash.out.CashMemberRepository;
import com.back.global.eventpublisher.DomainEventPublisher;
import com.back.shared.job.dto.JobDto;
import com.back.shared.job.event.JobReadyInitEvent;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.member.event.CashMemberCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class CashSyncMemberUseCase {

    private final CashMemberRepository cashMemberRepository;
    private final DomainEventPublisher eventPublisher;
    private final boolean useKafkaEvent;

    private boolean checkExecuteDataInit = true;

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
                            _member.toDto()
                    )
            );
        }

        if (useKafkaEvent && checkExecuteDataInit && isReadyInitData()) {
            eventPublisher.publish(new JobReadyInitEvent(JobDto.readyByCashMember()));
            checkExecuteDataInit = false;
        }

        return _member;
    }

    private boolean isReadyInitData() {
        Optional<CashMember> user1Member = cashMemberRepository.findByUsername("user1");
        Optional<CashMember> user2Member = cashMemberRepository.findByUsername("user2");
        Optional<CashMember> user3Member = cashMemberRepository.findByUsername("user3");

        return user1Member.isPresent() && user2Member.isPresent() && user3Member.isPresent();
    }
}
