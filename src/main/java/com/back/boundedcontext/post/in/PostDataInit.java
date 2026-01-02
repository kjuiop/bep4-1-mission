package com.back.boundedcontext.post.in;

import com.back.boundedcontext.post.app.PostFacade;
import com.back.boundedcontext.post.domain.Post;
import com.back.boundedcontext.post.domain.PostComment;
import com.back.boundedcontext.post.domain.PostMember;
import com.back.global.rsdata.RsData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@ConditionalOnProperty(name = "app.data-init.enabled", havingValue = "true", matchIfMissing = true)
@Configuration
@Slf4j
public class PostDataInit {

    // Self-Injection 패턴 : 같은 클래스 내에서 트랜잭션 메서드를 호출할 수 있도록 하는 패턴
    // 자기 자신의 프록시를 참조하도록 설정
    private final PostDataInit self;
    private final PostFacade postFacade;
    private final boolean useKafkaEvent;

    public PostDataInit(
            @Lazy PostDataInit self,
            PostFacade postFacade,
            boolean useKafkaEvent
            ) {
        this.self = self;
        this.postFacade = postFacade;
        this.useKafkaEvent = useKafkaEvent;
    }

    @Bean
    @Order(3)
    public ApplicationRunner postDataInitApplicationRunner() {
        return args -> {
            if (useKafkaEvent) {
                return;
            }

            // 자기 자신을 의존성을 주입 (DI) 해서 함수를 호출함으로 SpringContext 프록시를 적용한 후에 함수를 수행할 수 있음
            // 따라서 @Transactional 어노테이션이 적용된 후에 함수가 동작함으로 기능도 정상 동작을 함
            self.makeBasePosts();
            self.makeBasePostComments();
            // 아래와 같은 방식은 클래스 내에서 자신의 메서드를 직접 호출함으로 proxy 가 개입할 여지가 없음
            // 따라서 @Transactional 어노테이션도 적용되지 않음
            // this.makeBaseMembers();
        };
    }

    @Transactional
    public void makeBasePosts() {
        if (postFacade.count() > 0) {
            return;
        }

        PostMember user1Member = postFacade.findMemberByUsername("user1").get();
        PostMember user2Member = postFacade.findMemberByUsername("user2").get();
        PostMember user3Member = postFacade.findMemberByUsername("user3").get();

        RsData<Post> post1 = postFacade.write(user1Member, "제목1", "내용1");
        log.debug(post1.getMsg());
        RsData<Post> post2 = postFacade.write(user1Member, "제목2", "내용2");
        log.debug(post2.getMsg());
        RsData<Post> post3 = postFacade.write(user1Member, "제목3", "내용3");
        log.debug(post3.getMsg());
        RsData<Post> post4 = postFacade.write(user2Member, "제목4", "내용4");
        log.debug(post4.getMsg());
        RsData<Post> post5 = postFacade.write(user2Member, "제목5", "내용5");
        log.debug(post5.getMsg());
        RsData<Post> post6 = postFacade.write(user3Member, "제목6", "내용6");
        log.debug(post6.getMsg());
    }

    @Transactional
    public void makeBasePostComments() {
        Post post1 = postFacade.findById(1).get();
        Post post2 = postFacade.findById(2).get();
        Post post3 = postFacade.findById(3).get();
        Post post4 = postFacade.findById(4).get();
        Post post5 = postFacade.findById(5).get();
        Post post6 = postFacade.findById(6).get();

        PostMember user1Member = postFacade.findMemberByUsername("user1").get();
        PostMember user2Member = postFacade.findMemberByUsername("user2").get();
        PostMember user3Member = postFacade.findMemberByUsername("user3").get();

        if (post1.hasComments()) return;

        RsData<PostComment> comment1 = postFacade.writeComment(post1, user1Member, "댓글1");
        log.debug(comment1.getMsg());
        RsData<PostComment> comment2 = postFacade.writeComment(post1, user2Member, "댓글2");
        log.debug(comment2.getMsg());
        RsData<PostComment> comment3 = postFacade.writeComment(post1, user3Member, "댓글3");
        log.debug(comment3.getMsg());

        RsData<PostComment> comment4 = postFacade.writeComment(post2, user2Member, "댓글4");
        log.debug(comment4.getMsg());
        RsData<PostComment> comment5 = postFacade.writeComment(post2, user2Member, "댓글5");
        log.debug(comment5.getMsg());

        RsData<PostComment> comment6 = postFacade.writeComment(post3, user3Member, "댓글6");
        log.debug(comment6.getMsg());

        RsData<PostComment> comment7 = postFacade.writeComment(post3, user1Member, "댓글7");
        log.debug(comment7.getMsg());

        RsData<PostComment> comment8 = postFacade.writeComment(post4, user1Member, "댓글8");
        log.debug(comment8.getMsg());
    }
}
