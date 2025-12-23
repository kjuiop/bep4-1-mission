package com.back.boundedcontext.member.eventlistener;

import com.back.boundedcontext.member.entity.Member;
import com.back.boundedcontext.post.event.CommentCreatedEvent;
import com.back.boundedcontext.post.event.PostCreatedEvent;
import com.back.boundedcontext.member.repository.MemberRepository;
import com.back.global.event.ActivityType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PostEventListener {

    private final MemberRepository memberRepository;

    @EventListener
    @Transactional
    public void handlePostCreated(PostCreatedEvent event) {
        Member author = event.getAuthor();
        ActivityType activityType = event.getActivityType();
        author.increaseActivityScore(activityType.getScore());
        memberRepository.save(author);

        logActivityScoreIncrease(author, activityType);

    }

    @EventListener
    @Transactional
    public void handleCommentCreated(CommentCreatedEvent event) {
        Member author = event.getAuthor();
        ActivityType activityType = event.getActivityType();
        author.increaseActivityScore(activityType.getScore());
        memberRepository.save(author);

        logActivityScoreIncrease(author, activityType);
    }

    private void logActivityScoreIncrease(Member member, ActivityType activityType) {
        log.info("활동점수 증가: 회원={}, 활동={}, 점수=+{}, 총점수={}",
                member.getUsername(),
                activityType.getDescription(),
                activityType.getScore(),
                member.getActivityScore());
    }

}
