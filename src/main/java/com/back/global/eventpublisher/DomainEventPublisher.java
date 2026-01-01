package com.back.global.eventpublisher;

/**
 * @author : JAKE
 * @date : 26. 1. 1.
 */
public interface DomainEventPublisher {
    void publish(Object event);
}
