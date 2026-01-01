package com.back.global.eventpublisher.topic;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

/**
 * @author : JAKE
 * @date : 26. 1. 1.
 */
@Getter
public class DomainEventEnvelope {

    private final String eventId;
    private final String eventType;
    private final Instant occurredAt;
    private final Object payload;

    public DomainEventEnvelope(Object event) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = event.getClass().getSimpleName();
        this.occurredAt = Instant.now();
        this.payload = event;
    }
}
