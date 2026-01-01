package com.back.global.eventpublisher.topic;

import com.back.shared.member.event.MemberJoinedEvent;
import org.springframework.stereotype.Component;

/**
 * @author : JAKE
 * @date : 26. 1. 1.
 */
@Component
public class KafkaTopicResolver implements TopicResolver {

    @Override
    public String resolveTopic(Object event) {

        if (event instanceof MemberJoinedEvent) {
            return "member-events";
        }

        return "unexpected-events";
    }
}
