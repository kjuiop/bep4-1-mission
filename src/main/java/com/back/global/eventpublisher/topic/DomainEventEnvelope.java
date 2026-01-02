package com.back.global.eventpublisher.topic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.UUID;

/**
 * @author : JAKE
 * @date : 26. 1. 1.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainEventEnvelope {

    private String eventId;
    private String eventType;
    private Instant occurredAt;
    private JsonNode payload;

    public static DomainEventEnvelope of(Object event, ObjectMapper om) {
        DomainEventEnvelope e = new DomainEventEnvelope();
        e.setEventId(UUID.randomUUID().toString());
        e.setEventType(event.getClass().getSimpleName());
        e.setOccurredAt(Instant.now());
        e.setPayload(om.valueToTree(event));
        return e;
    }
}
