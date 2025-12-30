package com.back.boundedcontext.payout.app;

import com.back.boundedcontext.payout.domain.PayoutCandidateItem;
import com.back.boundedcontext.payout.domain.PayoutEventType;
import com.back.boundedcontext.payout.domain.PayoutMember;
import com.back.boundedcontext.payout.out.PayoutCandidateItemRepository;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.market.dto.OrderItemDto;
import com.back.shared.market.out.MarketApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutAddPayoutCandidateItemsUseCase {

    private final MarketApiClient marketApiClient;
    private final PayoutSupport payoutSupport;
    private final PayoutCandidateItemRepository payoutCandidateItemRepository;

    public void addPayoutCandidateItems(OrderDto order) {
        marketApiClient.getOrderItems(order.getId())
                .forEach(orderItem -> makePayoutCandidateItems(order, orderItem));
    }

    private void makePayoutCandidateItems(OrderDto order, OrderItemDto orderItem) {
        PayoutMember system = payoutSupport.findSystemMember().get();
        PayoutMember buyer = payoutSupport.findMemberById(order.getBuyerId()).get();
        PayoutMember seller = payoutSupport.findMemberById(orderItem.getSellerId()).get();

        makePayoutCandidateItem(
            PayoutEventType.정산_상품판매_수수료,
            orderItem.getModelTypeCode(),
            orderItem.getId(),
            order.getPaymentDate(),
            buyer,
            system,
            orderItem.getPayoutFee()
        );

        makePayoutCandidateItem(
                PayoutEventType.정산_상품판매_대금,
                orderItem.getModelTypeCode(),
                orderItem.getId(),
                order.getPaymentDate(),
                buyer,
                seller,
                orderItem.getSalePriceWithoutFee()
        );
    }

    private void makePayoutCandidateItem(
            PayoutEventType eventType,
            String relTypeCode,
            long relId,
            LocalDateTime paymentDate,
            PayoutMember payer,
            PayoutMember payee,
            long amount
    ) {
        PayoutCandidateItem payoutCandidateItem = new PayoutCandidateItem(
                eventType,
                relTypeCode,
                relId,
                paymentDate,
                payer,
                payee,
                amount
        );

        payoutCandidateItemRepository.save(payoutCandidateItem);
    }
}
