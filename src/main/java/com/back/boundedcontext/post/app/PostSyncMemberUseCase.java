package com.back.boundedcontext.post.app;

import com.back.boundedcontext.post.domain.PostMember;
import com.back.boundedcontext.post.out.PostMemberRepository;
import com.back.global.eventpublisher.DomainEventPublisher;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.post.event.PostReadyInitEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class PostSyncMemberUseCase {

    private final PostMemberRepository postMemberRepository;
    private final DomainEventPublisher eventPublisher;

    private boolean checkExecutePostDataInit = true;

    public PostMember syncMember(MemberDto member) {
        PostMember postMember = new PostMember(
                member.getId(),
                member.getCreateDate(),
                member.getModifyDate(),
                member.getUsername(),
                "",
                member.getNickname(),
                member.getActivityScore()
        );

        PostMember saved =  postMemberRepository.save(postMember);

        // 서버가 1대일 때 사용, 2대 이상일 때에는 DB 에 상태 저장 필요
        if (checkExecutePostDataInit && isReadyInitData()) {
            eventPublisher.publish(new PostReadyInitEvent());
            // 수행 완료
            checkExecutePostDataInit = false;
        }


        return saved;
    }

    private boolean isReadyInitData() {
        Optional<PostMember> user1Member = postMemberRepository.findByUsername("user1");
        Optional<PostMember> user2Member = postMemberRepository.findByUsername("user2");
        Optional<PostMember> user3Member = postMemberRepository.findByUsername("user3");

        return user1Member.isPresent() && user2Member.isPresent() && user3Member.isPresent();
    }
}
