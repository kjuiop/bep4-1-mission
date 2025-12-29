package com.back.boundedcontext.market.domain;

import com.back.shared.member.domain.ReplicaMember;
import com.back.shared.member.dto.MarketMemberDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Entity
@Table(name = "MARKET_MEMBER")
@Getter
@NoArgsConstructor
public class MarketMember extends ReplicaMember {
    public MarketMember(long id, LocalDateTime createDate, LocalDateTime modifyDate, String username, String password, String nickname, int activityScore) {
        super(id, createDate, modifyDate, username, password, nickname, activityScore);
    }

    public MarketMemberDto toDto() {
        return new MarketMemberDto(
                getId(),
                getCreateDate(),
                getModifyDate(),
                getUsername(),
                getNickname(),
                getActivityScore()
        );
    }
}
