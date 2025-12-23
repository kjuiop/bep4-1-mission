package com.back.boundedcontext.post.event;

import com.back.boundedcontext.member.entity.Member;
import com.back.global.event.ActivityType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Getter
@RequiredArgsConstructor
public class PostCreatedEvent {

    private final Long postId;
    private final String title;
    private final String content;
    private final ActivityType activityType;
    private final Member author;
}
