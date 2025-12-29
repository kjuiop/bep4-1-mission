package com.back.boundedcontext.payout.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
@Entity
@Table(name = "PAYOUT_PAYOUT_CANDIDATE_ITEM")
@NoArgsConstructor
@Getter
public class PayoutCandidateItem extends BaseIdAndTime {

    @Enumerated(EnumType.STRING)
    private PayoutEventType eventType;

    private String relTypecode;

    private long relId;

    private LocalDateTime paymentDate;

    @ManyToOne(fetch = LAZY)
    private PayoutMember payer;

    @ManyToOne(fetch = LAZY)
    private PayoutMember payee;

    private long amount;

    @OneToOne(fetch = LAZY)
    @Setter
    private PayoutItem payoutItem;

    public PayoutCandidateItem(PayoutEventType eventType, String relTypecode, long relId, LocalDateTime paymentDate, PayoutMember payer, PayoutMember payee, long amount) {
        this.eventType = eventType;
        this.relTypecode = relTypecode;
        this.relId = relId;
        this.paymentDate = paymentDate;
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
    }
}
