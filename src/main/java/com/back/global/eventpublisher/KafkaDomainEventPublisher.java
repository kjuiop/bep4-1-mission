package com.back.global.eventpublisher;

import com.back.global.eventpublisher.topic.TopicResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author : JAKE
 * @date : 26. 1. 1.
 */
@RequiredArgsConstructor
public class KafkaDomainEventPublisher implements DomainEventPublisher{

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TopicResolver topicResolver;

    @Override
    public void publish(Object event) {
    }
}
