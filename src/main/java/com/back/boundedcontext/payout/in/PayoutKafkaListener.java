package com.back.boundedcontext.payout.in;

import com.back.boundedcontext.payout.app.PayoutFacade;
import com.back.global.eventpublisher.topic.DomainEventEnvelope;
import com.back.shared.market.event.MarketOrderPaymentCompletedEvent;
import com.back.shared.member.event.MemberJoinedEvent;
import com.back.shared.member.event.MemberModifiedEvent;
import com.back.shared.payout.event.PayoutCompletedEvent;
import com.back.shared.payout.event.PayoutMemberCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

/**
  * @author : JAKE
  * @date : 26. 1. 2.
*/
@Slf4j
@Component
@RequiredArgsConstructor
public class PayoutKafkaListener {

    private final PayoutFacade payoutFacade;
    private final PayoutDataInit payoutDataInit;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "member-events", groupId = "payout-service")
    @Transactional(propagation = REQUIRES_NEW)
    public void memberEventHandle(String value) {

        try {
            DomainEventEnvelope envelope = objectMapper.readValue(value, DomainEventEnvelope.class);

            switch (envelope.getEventType()) {

                case "MemberJoinedEvent" -> {
                    MemberJoinedEvent event = objectMapper.treeToValue(envelope.getPayload(), MemberJoinedEvent.class);
                    payoutFacade.syncMember(event.getMember());
                }

                case "MemberModifiedEvent" -> {
                    MemberModifiedEvent event = objectMapper.treeToValue(envelope.getPayload(), MemberModifiedEvent.class);
                    payoutFacade.syncMember(event.getMember());
                }

                default -> {
                    // ignore
                }
            }
        } catch (Exception e) {
            log.error("Kafka consume failed. raw={}", value, e);
            throw e;
        }
    }

    @KafkaListener(topics = "market-events", groupId = "payout-service")
    @Transactional(propagation = REQUIRES_NEW)
    public void marketEventHandle(String value) {

        try {
            DomainEventEnvelope envelope = objectMapper.readValue(value, DomainEventEnvelope.class);

            switch (envelope.getEventType()) {

                case "MarketOrderPaymentCompletedEvent" -> {
                    MarketOrderPaymentCompletedEvent event = objectMapper.treeToValue(envelope.getPayload(), MarketOrderPaymentCompletedEvent.class);
                    payoutFacade.addPayoutCandidateItems(event.getOrder());
                }

                default -> {
                    // ignore
                }
            }
        } catch (Exception e) {
            log.error("Kafka consume failed. raw={}", value, e);
            throw e;
        }
    }

    @KafkaListener(topics = "payout-events", groupId = "payout-service")
    @Transactional(propagation = REQUIRES_NEW)
    public void payoutEventHandle(String value) {

        try {
            DomainEventEnvelope envelope = objectMapper.readValue(value, DomainEventEnvelope.class);

            switch (envelope.getEventType()) {

                case "PayoutMemberCreatedEvent" -> {
                    PayoutMemberCreatedEvent event = objectMapper.treeToValue(envelope.getPayload(), PayoutMemberCreatedEvent.class);
                    payoutFacade.createPayout(event.getMember().getId());
                }

                case "PayoutCompletedEvent" -> {
                    PayoutCompletedEvent event = objectMapper.treeToValue(envelope.getPayload(), PayoutCompletedEvent.class);
                    payoutFacade.createPayout(event.getPayout().getPayeeId());
                }

                case "PayoutReadyInitEvent" -> {
                    payoutDataInit.forceMakePayoutReadyCandidatesItems();
                    payoutDataInit.collectPayoutItemsMore();
                    payoutDataInit.completePayoutsMore();
                }

                default -> {
                    // ignore
                }
            }
        } catch (Exception e) {
            log.error("Kafka consume failed. raw={}", value, e);
            throw e;
        }
    }
}
