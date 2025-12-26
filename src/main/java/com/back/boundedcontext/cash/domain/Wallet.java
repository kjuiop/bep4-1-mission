package com.back.boundedcontext.cash.domain;

import com.back.global.jpa.entity.BaseEntity;
import com.back.global.jpa.entity.BaseManualIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Entity
@Table(name = "CASH_WALLET")
@NoArgsConstructor
public class Wallet extends BaseManualIdAndTime {

    @Getter
    private long balance;

    @OneToMany(mappedBy = "wallet", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<CashLog> cashLogs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private CashMember holder;

    public Wallet(CashMember holder) {
        super(holder.getId());
        this.holder = holder;
    }

    public boolean hasBalance() {
        return balance > 0;
    }

    public void credit(long amount, CashLog.EventType eventType, String relTypeCode, long relId) {
        balance += amount;

        addCashLog(amount, eventType, relTypeCode, relId);
    }

    public void credit(long amount, CashLog.EventType eventType, BaseEntity rel) {
        credit(amount, eventType, rel.getModelTypeCode(), rel.getId());
    }

    public void credit(long amount, CashLog.EventType eventType) {
        credit(amount, eventType, holder);
    }

    public void debit(long amount, CashLog.EventType eventType, String relTypeCode, long relId) {
        balance -= amount;

        addCashLog(-amount, eventType, relTypeCode, relId);
    }

    public void debit(long amount, CashLog.EventType eventType, BaseEntity rel) {
        debit(amount, eventType, rel.getModelTypeCode(), rel.getId());
    }

    public void debit(long amount, CashLog.EventType eventType) {
        debit(amount, eventType, holder);
    }

    private CashLog addCashLog(long amount, CashLog.EventType eventType, String relTypeCode, long relId) {
        CashLog cashLog = new CashLog(
                eventType,
                relTypeCode,
                relId,
                holder,
                this,
                amount,
                balance
        );
        cashLogs.add(cashLog);
        return cashLog;
    }
}
