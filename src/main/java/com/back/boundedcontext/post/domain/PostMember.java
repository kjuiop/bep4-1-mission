package com.back.boundedcontext.post.domain;

import com.back.global.jpa.entity.BaseIdAndTimeManual;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Entity
@Table(name = "POST_MEMBER")
@NoArgsConstructor
@Getter
public class PostMember extends BaseIdAndTimeManual {

    @Column(unique = true)
    private String username;

    private String password;

    private String nickname;

    private int activityScore;
}
