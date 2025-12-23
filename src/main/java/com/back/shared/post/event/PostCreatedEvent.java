package com.back.shared.post.event;

import com.back.shared.post.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Getter
@AllArgsConstructor
public class PostCreatedEvent {
    private final PostDto post;
    private final int score = 3;
}
