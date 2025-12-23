package com.back.entity;

import com.back.jpa.entity.BaseIdAndTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

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

    public Post(Member author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
