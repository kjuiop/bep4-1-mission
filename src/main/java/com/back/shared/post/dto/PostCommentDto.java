package com.back.shared.post.dto;

import com.back.boundedcontext.post.entity.PostComment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@AllArgsConstructor
@Getter
public class PostCommentDto {

    private final long id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final long postId;
    private final long authorId;
    private final String authorName;
    private final String content;

    public PostCommentDto(PostComment comment) {
        this(
                comment.getId(),
                comment.getCreateDate(),
                comment.getModifyDate(),
                comment.getPost().getId(),
                comment.getAuthor().getId(),
                comment.getAuthor().getUsername(),
                comment.getContent()
        );
    }
}
