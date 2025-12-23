package com.back.shared.member.dto;

import com.back.boundedcontext.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 24.
 */
@AllArgsConstructor
@Getter
public class MemberDto {
    private final long id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final String username;
    private final String nickname;

    public MemberDto(Member member) {
        this(
                member.getId(),
                member.getCreateDate(),
                member.getModifyDate(),
                member.getUsername(),
                member.getNickname()
        );
    }
}
