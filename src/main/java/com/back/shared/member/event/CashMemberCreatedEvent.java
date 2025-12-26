package com.back.shared.member.event;

import com.back.shared.member.dto.CashMemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Getter
@AllArgsConstructor
public class CashMemberCreatedEvent {
    private final CashMemberDto member;
}
