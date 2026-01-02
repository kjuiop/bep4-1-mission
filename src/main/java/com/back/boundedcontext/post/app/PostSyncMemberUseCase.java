package com.back.boundedcontext.post.app;

import com.back.boundedcontext.post.domain.PostMember;
import com.back.boundedcontext.post.out.PostMemberRepository;
import com.back.global.eventpublisher.DomainEventPublisher;
import com.back.shared.job.dto.JobDto;
import com.back.shared.job.event.JobReadyInitEvent;
import com.back.shared.member.dto.MemberDto;
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

    private boolean checkExecuteDataInit = true;

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

        if (checkExecuteDataInit && isReadyInitData()) {
            eventPublisher.publish(new JobReadyInitEvent(JobDto.readyByPostMember()));
            checkExecuteDataInit = false;
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
