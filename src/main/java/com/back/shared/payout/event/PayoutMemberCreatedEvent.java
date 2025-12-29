package com.back.shared.payout.event;

import com.back.shared.payout.dto.PayoutMemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
@Getter
@AllArgsConstructor
public class PayoutMemberCreatedEvent {
    private final PayoutMemberDto member;
}
