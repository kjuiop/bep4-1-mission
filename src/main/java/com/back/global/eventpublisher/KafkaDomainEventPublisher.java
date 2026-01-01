package com.back.global.eventpublisher;

import com.back.global.eventpublisher.topic.DomainEventEnvelope;
import com.back.global.eventpublisher.topic.KafkaResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author : JAKE
 * @date : 26. 1. 1.
 */
@RequiredArgsConstructor
public class KafkaDomainEventPublisher implements DomainEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaResolver kafkaResolver;

    @Override
    public void publish(Object event) {
        String topic = kafkaResolver.resolveTopic(event);
        String key = kafkaResolver.resolveKey(event);
        DomainEventEnvelope envelope = new DomainEventEnvelope(event);

        kafkaTemplate.send(
                MessageBuilder
                        .withPayload(envelope)
                        .setHeader(KafkaHeaders.TOPIC, topic)
                        .setHeader(KafkaHeaders.KEY, key)
                        .setHeader("eventType", envelope.getEventType())
                        .setHeader("eventId", envelope.getEventId())
                        .build()
        );
    }
}
