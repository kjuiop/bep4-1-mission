package com.back.boundedcontext.member.app;

import com.back.boundedcontext.member.domain.Member;
import com.back.boundedcontext.member.out.repository.MemberRepository;
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

    public long count() {
        return memberRepository.count();
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Optional<Member> findById(long id) {
        return memberRepository.findById(id);
    }
}
