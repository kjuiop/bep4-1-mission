package com.back.boundedcontext.post.domain;

import com.back.boundedcontext.member.domain.Member;
import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.post.dto.PostDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Entity
@Table(name = "POST_POST")
@Getter
@NoArgsConstructor
public class Post extends BaseIdAndTime {

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @ManyToOne
    private PostMember author;

    @OneToMany(mappedBy = "post", cascade = {PERSIST, MERGE, REMOVE}, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    public Post(PostMember author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public PostComment addComment(PostMember author, String content) {
        PostComment comment = new PostComment(this, author, content);
        comments.add(comment);
        return comment;
    }

    public boolean hasComments() {
        return !comments.isEmpty();
    }

    public PostDto toDto() {
        return new PostDto(
                getId(),
                getCreateDate(),
                getModifyDate(),
                author.getId(),
                author.getNickname(),
                title,
                content
        );
    }
}
