package com.back.boundedcontext.post.domain;

import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Entity
@Table(name = "POST_MEMBER")
@NoArgsConstructor
@Getter
public class PostMember extends ReplicaMember {
    public PostMember(long id, LocalDateTime createDate, LocalDateTime modifyDate, String username, String password, String nickname, int activityScore) {
        super(id, createDate, modifyDate, username, password, nickname, activityScore);
    }
}
