package com.back.shared.payout.dto;

import com.back.standard.modeltype.CanGetModelTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 30.
 */
@AllArgsConstructor
@Getter
public class PayoutDto implements CanGetModelTypeCode {

    private final long id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private long payeeId;
    private String payeeName;
    private LocalDateTime payoutDate;
    private long amount;
    private boolean isPayeeSystem;

    @Override
    public String getModelTypeCode() {
        return "Payout";
    }
}
