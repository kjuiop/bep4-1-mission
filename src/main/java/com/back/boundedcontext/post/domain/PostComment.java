package com.back.boundedcontext.post.domain;

import com.back.boundedcontext.member.domain.Member;
import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.post.dto.PostCommentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Entity
@Table(name = "POST_POST_COMMENT")
@Getter
@NoArgsConstructor
public class PostComment extends BaseIdAndTime {

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private PostMember author;

    public PostComment(Post post, PostMember author, String content) {
        this.post = post;
        this.author = author;
        this.content = content;
    }

    public PostCommentDto toDto() {
        return new PostCommentDto(
                getId(),
                getCreateDate(),
                getModifyDate(),
                post.getId(),
                author.getId(),
                author.getNickname(),
                content
        );
    }
}
