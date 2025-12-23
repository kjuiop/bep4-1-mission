package com.back.global.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Getter
@RequiredArgsConstructor
public enum ActivityType {

    POST_CREATED(3, "게시글 작성"),
    COMMENT_CREATED(1, "댓글 작성");

    private final int score;
    private final String description;
}
