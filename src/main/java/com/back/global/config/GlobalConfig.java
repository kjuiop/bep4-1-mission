package com.back.global.config;

import com.back.global.eventpublisher.DomainEventPublisher;
import com.back.global.eventpublisher.KafkaDomainEventPublisher;
import com.back.global.eventpublisher.SpringDomainEventPublisher;
import com.back.global.eventpublisher.topic.TopicResolver;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Configuration
public class GlobalConfig {

    public static String INTERNAL_CALL_BACK_URL;

    @Getter
    private static DomainEventPublisher eventPublisher;

    @Bean
    public DomainEventPublisher domainEventPublisher(
            @Value("${app.event-publisher.type:spring}") String type,
            ApplicationEventPublisher applicationEventPublisher,
            KafkaTemplate<String, Object> kafkaTemplate,
            TopicResolver topicResolver
    ) {
        DomainEventPublisher publisher =
                "kafka".equalsIgnoreCase(type)
                        ? new KafkaDomainEventPublisher(kafkaTemplate, topicResolver)
                        : new SpringDomainEventPublisher(applicationEventPublisher);

        GlobalConfig.eventPublisher = publisher;
        return publisher;
    }
}
