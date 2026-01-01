package com.back.global.eventpublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@RequiredArgsConstructor
public class SpringDomainEventPublisher implements DomainEventPublisher {
    private final ApplicationEventPublisher delegate;

    @Override
    public void publish(Object event) {
        delegate.publishEvent(event);
    }
}
