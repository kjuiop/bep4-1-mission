package com.back.initdata;

import com.back.entity.Member;
import com.back.service.MemberService;
import lombok.RequiredArgsConstructor;
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
    private final MemberService memberService;

    public DataInit(@Lazy DataInit self, MemberService memberService) {
        this.self = self;
        this.memberService = memberService;
    }

    @Bean
    public ApplicationRunner baseInitDataRunner() {
        return args -> {
            // 자기 자신을 의존성을 주입 (DI) 해서 함수를 호출함으로 SpringContext 프록시를 적용한 후에 함수를 수행할 수 있음
            // 따라서 @Transactional 어노테이션이 적용된 후에 함수가 동작함으로 기능도 정상 동작을 함
            self.makeBaseMembers();

            // 아래와 같은 방식은 클래스 내에서 자신의 메서드를 직접 호출함으로 proxy 가 개입할 여지가 없음
            // 따라서 @Transactional 어노테이션도 적용되지 않음
            // this.makeBaseMembers();
        };
    }

    @Transactional
    public void makeBaseMembers() {
        if (memberService.count() > 0) {
            return;
        }

        Member systemMember = memberService.join("system", "1234", "시스템");
        Member holdingMember = memberService.join("holding", "1234", "홀딩");
        Member adminMember = memberService.join("admin", "1234", "관리자");
        Member user1Member = memberService.join("user1", "1234", "유저1");
        Member user2Member = memberService.join("user2", "1234", "유저2");
        Member user3Member = memberService.join("user3", "1234", "유저3");
    }
}
