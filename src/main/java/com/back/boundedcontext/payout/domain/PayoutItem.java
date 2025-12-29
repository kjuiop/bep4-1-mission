package com.back.boundedcontext.payout.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
@Entity
@Table(name = "PAYOUT_PAYOUT_ITEM")
@NoArgsConstructor
public class PayoutItem extends BaseIdAndTime {

    @ManyToOne(fetch = LAZY)
    private Payout payout;

    @Enumerated(EnumType.STRING)
    private PayoutEventType eventType;

    private String relTypeCode;

    private long relId;

    private LocalDateTime paymentDate;

    @ManyToOne(fetch = LAZY)
    private PayoutMember payer;

    @ManyToOne(fetch = LAZY)
    private PayoutMember payee;

    private long amount;

    public PayoutItem(Payout payout, PayoutEventType eventType, String relTypeCode, long relId, LocalDateTime paymentDate, PayoutMember payer, PayoutMember payee, long amount) {
        this.payout = payout;
        this.eventType = eventType;
        this.relTypeCode = relTypeCode;
        this.relId = relId;
        this.paymentDate = paymentDate;
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
    }
}
