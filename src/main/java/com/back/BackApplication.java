package com.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackApplication {

    // @SpringBootApplication 내부 @ComponentScan 어노테이션의 기능에 따라 애플리케이션 시작 시 전체 컴포넌트를 스캔하게 되어있음
    // 최초 실행 시 @Bean, @Service, @Repository 와 같은 컴포넌트를 프레임워크에 등로갛게 됨
    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }

}
