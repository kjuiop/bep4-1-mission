package com.back.boundedcontext.cash.app;

import com.back.boundedcontext.cash.domain.CashLog;
import com.back.boundedcontext.cash.domain.Wallet;
import com.back.shared.payout.dto.PayoutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 25. 12. 30.
 */
@Service
@RequiredArgsConstructor
public class CashCompletePayoutUseCase {

    private final CashSupport cashSupport;

    public void completePayout(PayoutDto payout) {
        Wallet holdingWallet = cashSupport.findHoldingWallet().get();
        Wallet payeeWallet = cashSupport.findWalletByHolderId(payout.getPayeeId()).get();

        holdingWallet.debit(
                payout.getAmount(),
                payout.isPayeeSystem() ? CashLog.EventType.정산지급_상품판매_수수료 : CashLog.EventType.정산지급_상품판매_대금,
                payout.getModelTypeCode(),
                payout.getId()
        );

        payeeWallet.credit(
                payout.getAmount(),
                payout.isPayeeSystem() ? CashLog.EventType.정산지급_상품판매_수수료 : CashLog.EventType.정산지급_상품판매_대금,
                payout.getModelTypeCode(),
                payout.getId()
        );
    }
}
