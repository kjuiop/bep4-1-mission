package com.back.boundedcontext.cash.app;

import com.back.boundedcontext.cash.domain.CashLog;
import com.back.boundedcontext.cash.domain.Wallet;
import com.back.global.eventpublisher.DomainEventPublisher;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSuccessdedEvent;
import com.back.shared.market.event.MarketOrderRequestPaymentStartedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
@RequiredArgsConstructor
public class CashOrderCompletePaymentUseCase {
    private final CashSupport cashSupport;
    private final DomainEventPublisher eventPublisher;

    public void handle(MarketOrderRequestPaymentStartedEvent event) {
        Wallet customerWallet = cashSupport.findWalletByHolderId(event.getOrder().getBuyerId()).get();
        Wallet holdingWallet = cashSupport.findHoldingWallet().get();

        if (event.getPgPaymentAmount() > 0) {
            customerWallet.credit(
                    event.getPgPaymentAmount(),
                    CashLog.EventType.충전_PG결제_토스페이먼츠,
                    "Order",
                    event.getOrder().getId()
            );
        }

        boolean canPay = customerWallet.getBalance() >= event.getOrder().getSalePrice();
        if (canPay) {
            customerWallet.debit(
                    event.getOrder().getSalePrice(),
                    CashLog.EventType.사용_주문결제,
                    "Order",
                    event.getOrder().getId()
            );

            holdingWallet.credit(
                    event.getOrder().getSalePrice(),
                    CashLog.EventType.임시보관_주문결제,
                    "Order",
                    event.getOrder().getId()
            );

            eventPublisher.publish(
                    new CashOrderPaymentSuccessdedEvent(
                            event.getOrder(),
                            event.getPgPaymentAmount()
                    )
            );
        } else {
            eventPublisher.publish(
                    new CashOrderPaymentFailedEvent(
                            "400-1",
                            "충전은 완료했지만 %d번 주문을 결제완료처리를 하기에는 예치금이 부족합니다.".formatted(event.getOrder().getId()),
                            event.getOrder(),
                            event.getPgPaymentAmount(),
                            event.getPgPaymentAmount() - customerWallet.getBalance()
                    )
            );
        }
    }
}
