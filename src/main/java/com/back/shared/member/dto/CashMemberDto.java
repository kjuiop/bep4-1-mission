package com.back.shared.member.dto;

import com.back.boundedcontext.cash.domain.CashMember;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Getter
@RequiredArgsConstructor
public class CashMemberDto {
    private final long id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final String username;
    private final String nickname;
    private final int activityScore;

    @JsonCreator
    public CashMemberDto(
            @JsonProperty("id") Long id,
            @JsonProperty("username") String username,
            @JsonProperty("nickname") String nickname,
            @JsonProperty("activityScore") Integer activityScore,
            @JsonProperty("createDate") LocalDateTime createDate,
            @JsonProperty("modifyDate") LocalDateTime modifyDate
    ) {
        this.id = id;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.username = username;
        this.nickname = nickname;
        this.activityScore = activityScore;
    }
}
