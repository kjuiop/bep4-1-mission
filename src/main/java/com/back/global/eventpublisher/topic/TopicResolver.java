package com.back.global.eventpublisher.topic;

/**
 * @author : JAKE
 * @date : 26. 1. 1.
 */
public interface TopicResolver {
    String resolveTopic(Object event);
}
