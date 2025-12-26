package com.back.boundedcontext.cash.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Entity
@Table(name = "CASH_CASH_LOG")
@NoArgsConstructor
public class CashLog extends BaseIdAndTime {

    public enum EventType {
        충전_무통장입금,
        충전_PG결제_토스페이먼츠,
        출금_통장입금,
        사용_주문결제,
        임시보관_주문결제,
        정산지급_상품판매_수수료,
        정산수정_상품판매_수수료,
        정산지급_상품판매_대금,
        정산수령_상품판매_대금,
    }

    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private String relTypeCode;
    private long relId;

    @ManyToOne(fetch = FetchType.LAZY)
    private CashMember member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;

    private long amount;
    private long balance;

    public CashLog(EventType eventType, String relTypeCode, long relId, CashMember member, Wallet wallet, long amount, long balance) {
        this.eventType = eventType;
        this.relTypeCode = relTypeCode;
        this.relId = relId;
        this.member = member;
        this.wallet = wallet;
        this.amount = amount;
        this.balance = balance;
    }
}
