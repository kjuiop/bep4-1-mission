package com.back.global.config;

import com.back.global.eventpublisher.DomainEventPublisher;
import com.back.global.eventpublisher.KafkaDomainEventPublisher;
import com.back.global.eventpublisher.SpringDomainEventPublisher;
import com.back.global.eventpublisher.topic.KafkaResolver;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import tools.jackson.databind.ObjectMapper;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Configuration
public class GlobalConfig {

    @Getter
    private static DomainEventPublisher eventPublisher;

    @Bean
    public DomainEventPublisher domainEventPublisher(
            @Value("${app.event-publisher.type:spring}") String type,
            ApplicationEventPublisher applicationEventPublisher,
            KafkaTemplate<String, Object> kafkaTemplate,
            KafkaResolver topicResolver,
            ObjectMapper objectMapper
    ) {
        DomainEventPublisher publisher =
                "kafka".equalsIgnoreCase(type)
                        ? new KafkaDomainEventPublisher(kafkaTemplate, topicResolver, objectMapper)
                        : new SpringDomainEventPublisher(applicationEventPublisher);

        GlobalConfig.eventPublisher = publisher;
        return publisher;
    }
}
