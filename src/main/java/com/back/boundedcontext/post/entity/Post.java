package com.back.boundedcontext.post.entity;

import com.back.boundedcontext.member.entity.Member;
import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Entity
@NoArgsConstructor
public class Post extends BaseIdAndTime {

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @ManyToOne
    private Member author;

    @OneToMany(mappedBy = "post", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    public Post(Member author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public PostComment addComment(Member author, String content) {
        PostComment comment = new PostComment(this, author, content);
        comments.add(comment);
        return comment;
    }

    public boolean hasComments() {
        return !comments.isEmpty();
    }
}
