package com.back.global.initdata;

import com.back.boundedcontext.member.domain.Member;
import com.back.boundedcontext.post.domain.Post;
import com.back.boundedcontext.member.app.MemberFacade;
import com.back.boundedcontext.post.app.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Configuration
@Slf4j
public class DataInit {

    // Self-Injection 패턴 : 같은 클래스 내에서 트랜잭션 메서드를 호출할 수 있도록 하는 패턴
    // 자기 자신의 프록시를 참조하도록 설정
    private final DataInit self;
    private final MemberFacade memberFacade;
    private final PostService postService;

    public DataInit(
            @Lazy DataInit self,
            MemberFacade memberFacade,
            PostService postService
            ) {
        this.self = self;
        this.memberFacade = memberFacade;
        this.postService = postService;
    }

    @Bean
    public ApplicationRunner baseInitDataRunner() {
        return args -> {
            // 자기 자신을 의존성을 주입 (DI) 해서 함수를 호출함으로 SpringContext 프록시를 적용한 후에 함수를 수행할 수 있음
            // 따라서 @Transactional 어노테이션이 적용된 후에 함수가 동작함으로 기능도 정상 동작을 함
            self.makeBaseMembers();
            self.makeBasePosts();
            self.makeBasePostComments();
            // 아래와 같은 방식은 클래스 내에서 자신의 메서드를 직접 호출함으로 proxy 가 개입할 여지가 없음
            // 따라서 @Transactional 어노테이션도 적용되지 않음
            // this.makeBaseMembers();
        };
    }

    @Transactional
    public void makeBaseMembers() {
        if (memberFacade.count() > 0) {
            return;
        }

        Member systemMember = memberFacade.join("system", "1234", "시스템");
        Member holdingMember = memberFacade.join("holding", "1234", "홀딩");
        Member adminMember = memberFacade.join("admin", "1234", "관리자");
        Member user1Member = memberFacade.join("user1", "1234", "유저1");
        Member user2Member = memberFacade.join("user2", "1234", "유저2");
        Member user3Member = memberFacade.join("user3", "1234", "유저3");
    }

    @Transactional
    public void makeBasePosts() {
        if (postService.count() > 0) {
            return;
        }

        Member user1Member = memberFacade.findByUsername("user1").get();
        Member user2Member = memberFacade.findByUsername("user2").get();
        Member user3Member = memberFacade.findByUsername("user3").get();

        Post post1 = postService.write(user1Member, "제목1", "내용1");
        Post post2 = postService.write(user1Member, "제목2", "내용2");
        Post post3 = postService.write(user1Member, "제목3", "내용3");
        Post post4 = postService.write(user2Member, "제목4", "내용4");
        Post post5 = postService.write(user2Member, "제목5", "내용5");
        Post post6 = postService.write(user3Member, "제목6", "내용6");
    }

    @Transactional
    public void makeBasePostComments() {
        Post post1 = postService.findById(1).get();
        Post post2 = postService.findById(2).get();
        Post post3 = postService.findById(3).get();
        Post post4 = postService.findById(4).get();
        Post post5 = postService.findById(5).get();
        Post post6 = postService.findById(6).get();

        Member user1Member = memberFacade.findByUsername("user1").get();
        Member user2Member = memberFacade.findByUsername("user2").get();
        Member user3Member = memberFacade.findByUsername("user3").get();

        if (post1.hasComments()) return;

        postService.writeComment(post1.getId(), user1Member, "댓글1");
        postService.writeComment(post1.getId(), user2Member, "댓글2");
        postService.writeComment(post1.getId(), user3Member, "댓글3");


        postService.writeComment(post2.getId(), user2Member, "댓글4");
        postService.writeComment(post2.getId(), user2Member, "댓글5");

        postService.writeComment(post3.getId(), user3Member, "댓글6");
        postService.writeComment(post3.getId(), user1Member, "댓글7");

        postService.writeComment(post4.getId(), user1Member, "댓글8");
    }
}
