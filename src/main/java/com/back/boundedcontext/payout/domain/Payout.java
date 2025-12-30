package com.back.boundedcontext.payout.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.payout.dto.PayoutDto;
import com.back.shared.payout.event.PayoutCompletedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
// 회원별 정산 금액
@Entity
@Table(name = "PAYOUT_PAYOUT")
@NoArgsConstructor
@Getter
public class Payout extends BaseIdAndTime {

    @ManyToOne(fetch = LAZY)
    private PayoutMember payee;

    @Setter
    private LocalDateTime payoutDate;

    private long amount;

    @OneToMany(mappedBy = "payout", cascade = {PERSIST, MERGE, REMOVE}, orphanRemoval = true)
    private List<PayoutItem> items = new ArrayList<>();

    public Payout(PayoutMember payee) {
        this.payee = payee;
    }

    public PayoutItem addItem(PayoutEventType eventType, String relTypeCode, long relId, LocalDateTime paymentDate, PayoutMember payer, PayoutMember payee, long amount) {
        PayoutItem payoutItem = new PayoutItem(
                this, eventType, relTypeCode, relId, paymentDate, payer, payee, amount
        );

        items.add(payoutItem);
        this.amount += amount;
        return payoutItem;
    }

    public void completePayout() {
        this.payoutDate = LocalDateTime.now();

        publishEvent(
                new PayoutCompletedEvent(
                        toDto()
                )
        );
    }

    public PayoutDto toDto() {
        return new PayoutDto(
                getId(),
                getCreateDate(),
                getModifyDate(),
                payee.getId(),
                payee.getNickname(),
                payoutDate,
                amount,
                payee.isSystem()
        );
    }
}
