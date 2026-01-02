package com.back.boundedcontext.member.app;

import com.back.boundedcontext.member.domain.Member;
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
    private final MemberGetRandomSecureTipUseCase memberGetRandomSecureTipUseCase;
    private final MemberSupport memberSupport;

    @Transactional
    public RsData<Member> join(String username, String password, String nickname) {
        return memberJoinUseCase.join(username, password, nickname);
    }

    @Transactional(readOnly = true)
    public long count() {
        return memberSupport.count();
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(long id) {
        return memberSupport.findById(id);
    }

    public String getRandomSecureTip() {
        return memberGetRandomSecureTipUseCase.getRandomSecureTip();
    }

    public void completeMemberDataInit() {
        memberSupport.completeMemberDataInit();
    }
}
