package com.back.shared.member.event;

import com.back.shared.member.dto.CashMemberDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Getter
public class CashMemberCreatedEvent {
    private final CashMemberDto member;

    @JsonCreator
    public CashMemberCreatedEvent(@JsonProperty("member") CashMemberDto member) {
        this.member = member;
    }
}
