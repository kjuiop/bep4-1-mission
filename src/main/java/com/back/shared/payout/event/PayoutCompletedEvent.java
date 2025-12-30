package com.back.shared.payout.event;

import com.back.shared.payout.dto.PayoutDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 25. 12. 30.
 */
@AllArgsConstructor
@Getter
public class PayoutCompletedEvent {
    private final PayoutDto payout;
}
