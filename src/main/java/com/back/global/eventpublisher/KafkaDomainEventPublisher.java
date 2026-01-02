package com.back.global.eventpublisher;

import com.back.global.eventpublisher.topic.DomainEventEnvelope;
import com.back.global.eventpublisher.topic.KafkaResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import tools.jackson.databind.ObjectMapper;

/**
 * @author : JAKE
 * @date : 26. 1. 1.
 */
@RequiredArgsConstructor
public class KafkaDomainEventPublisher implements DomainEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaResolver kafkaResolver;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(Object event) {
        String topic = kafkaResolver.resolveTopic(event);
        String key = kafkaResolver.resolveKey(event);
        DomainEventEnvelope envelope = DomainEventEnvelope.of(event, objectMapper);
        String payload = objectMapper.writeValueAsString(envelope);

        kafkaTemplate.send(
                MessageBuilder
                        .withPayload(payload)
                        .setHeader(KafkaHeaders.TOPIC, topic)
                        .setHeader(KafkaHeaders.KEY, key)
                        .setHeader("eventType", envelope.getEventType())
                        .setHeader("eventId", envelope.getEventId())
                        .build()
        );
    }
}
