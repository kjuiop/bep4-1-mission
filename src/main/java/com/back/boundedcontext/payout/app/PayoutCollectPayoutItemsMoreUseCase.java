package com.back.boundedcontext.payout.app;

import com.back.boundedcontext.payout.domain.*;
import com.back.boundedcontext.payout.out.PayoutCandidateItemRepository;
import com.back.boundedcontext.payout.out.PayoutRepository;
import com.back.global.rsdata.RsData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutCollectPayoutItemsMoreUseCase {

    private final PayoutRepository payoutRepository;
    private final PayoutCandidateItemRepository payoutCandidateItemRepository;

    public RsData<Integer> collectPayoutItemsMore(int limit) {
        List<PayoutCandidateItem> payoutReadyCandidateItems = findPayoutReadyCandidateItems(limit);

        if (payoutReadyCandidateItems.isEmpty()) {
            return new RsData<>("200-1", "더 이상 정산에 추가할 항목이 없습니다.", 0);
        }

        payoutReadyCandidateItems.stream()
                .collect(Collectors.groupingBy(PayoutCandidateItem::getPayee))
                .forEach((payee, candidateItems) -> {

                    Payout payout = findActiveByPayee(payee).get();

                    candidateItems.forEach(item -> {
                        PayoutItem payoutItem = payout.addItem(
                                item.getEventType(),
                                item.getRelTypecode(),
                                item.getRelId(),
                                item.getPaymentDate(),
                                item.getPayer(),
                                item.getPayee(),
                                item.getAmount()
                        );

                        item.setPayoutItem(payoutItem);
                    });
                });

        return new RsData<>(
                "201-1",
                "%d건의 정산데이터가 생성되었습니다.".formatted(payoutReadyCandidateItems.size()),
                payoutReadyCandidateItems.size()
        );
    }

    private List<PayoutCandidateItem> findPayoutReadyCandidateItems(int limit) {
        LocalDateTime daysAgo = LocalDateTime
                .now()
                .minusDays(PayoutPolicy.getPayoutReadyWaitingDays())
                .toLocalDate()
                .atStartOfDay();

        return payoutCandidateItemRepository.findByPayoutItemIsNullAndPaymentDateBeforeOrderByPayeeAscIdAsc(
                daysAgo,
                PageRequest.of(0, limit)
        );
    }

    private Optional<Payout> findActiveByPayee(PayoutMember payee) {
        return payoutRepository.findByPayeeAndPayoutDateIsNull(payee);
    }
}
