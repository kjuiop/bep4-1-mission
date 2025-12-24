package com.back.shared.member.event;

import com.back.shared.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 25. 12. 24.
 */
@Getter
@AllArgsConstructor
public class MemberModifiedEvent {
    private final MemberDto member;
}
