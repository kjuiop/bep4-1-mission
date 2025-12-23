package com.back.boundedcontext.member.app;

import com.back.boundedcontext.member.domain.Member;
import com.back.global.exception.DomainException;
import com.back.boundedcontext.member.out.MemberRepository;
import com.back.global.rsdata.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Service
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberJoinUseCase memberJoinUseCase;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public long count() {
        return memberRepository.count();
    }

    @Transactional
    public RsData<Member> join(String username, String password, String nickname) {
        findByUsername(username).ifPresent(member -> {
            throw new DomainException("409-1", "이미 존재하는 username 입니다.");
        });

        return memberJoinUseCase.join(username, password, nickname);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(long id) {
        return memberRepository.findById(id);
    }
}
