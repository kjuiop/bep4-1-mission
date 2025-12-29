package com.back.global.config;

import com.back.global.eventpublisher.EventPublisher;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Configuration
public class GlobalConfig {

    public static String INTERNAL_CALL_BACK_URL;

    @Getter
    private static EventPublisher eventPublisher;

    @Autowired
    public void setEventPublisher(EventPublisher eventPublisher) {
        GlobalConfig.eventPublisher = eventPublisher;
    }
}
