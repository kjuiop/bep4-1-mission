package com.back.shared.member.dto;

import com.back.boundedcontext.cash.domain.CashMember;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@AllArgsConstructor
@Getter
public class CashMemberDto {
    private final long id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final String username;
    private final String nickname;
    private final int activityScore;

    public CashMemberDto(CashMember member) {
        this(
                member.getId(),
                member.getCreateDate(),
                member.getModifyDate(),
                member.getUsername(),
                member.getNickname(),
                member.getActivityScore()
        );
    }
}
