package com.back.boundedcontext.member.app;

import com.back.boundedcontext.member.domain.Member;
import com.back.boundedcontext.member.out.repository.MemberRepository;
import com.back.global.eventpublisher.DomainEventPublisher;
import com.back.shared.member.event.MemberInitCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Component
@RequiredArgsConstructor
public class MemberSupport {

    private final MemberRepository memberRepository;
    private final DomainEventPublisher eventPublisher;

    public long count() {
        return memberRepository.count();
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Optional<Member> findById(long id) {
        return memberRepository.findById(id);
    }

    public void completeMemberDataInit() {
        eventPublisher.publish(new MemberInitCompletedEvent());
    }
}
