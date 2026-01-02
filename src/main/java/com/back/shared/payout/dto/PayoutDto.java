package com.back.shared.payout.dto;

import com.back.standard.modeltype.CanGetModelTypeCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 30.
 */

@Getter
@RequiredArgsConstructor
public class PayoutDto implements CanGetModelTypeCode {

    private final long id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private long payeeId;
    private String payeeName;
    private LocalDateTime payoutDate;
    private long amount;
    private boolean payeeSystem;

    @JsonCreator
    public PayoutDto(
            @JsonProperty("id") Long id,
            @JsonProperty("createDate") LocalDateTime createDate,
            @JsonProperty("modifyDate") LocalDateTime modifyDate,
            @JsonProperty("payeeId") long payeeId,
            @JsonProperty("payeeName") String payeeName,
            @JsonProperty("payoutDate") LocalDateTime payoutDate,
            @JsonProperty("amount") long amount,
            @JsonProperty("payeeSystem") boolean payeeSystem
    ) {
        this.id = id;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.payeeId = payeeId;
        this.payeeName = payeeName;
        this.payoutDate = payoutDate;
        this.amount = amount;
        this.payeeSystem = payeeSystem;
    }

    @Override
    public String getModelTypeCode() {
        return "Payout";
    }
}
