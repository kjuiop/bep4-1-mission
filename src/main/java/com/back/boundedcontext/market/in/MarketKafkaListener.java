package com.back.boundedcontext.market.in;

import com.back.boundedcontext.market.app.MarketFacade;
import com.back.global.eventpublisher.topic.DomainEventEnvelope;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSuccessdedEvent;
import com.back.shared.member.event.MarketMemberCreatedEvent;
import com.back.shared.member.event.MemberJoinedEvent;
import com.back.shared.member.event.MemberModifiedEvent;
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
public class MarketKafkaListener {

    private final MarketFacade marketFacade;
    private final MarketDataInit marketDataInit;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "member-events", groupId = "market-service")
    @Transactional(propagation = REQUIRES_NEW)
    public void memberEventHandle(String value) {

        try {
            DomainEventEnvelope envelope = objectMapper.readValue(value, DomainEventEnvelope.class);

            switch (envelope.getEventType()) {

                case "MemberJoinedEvent" -> {
                    MemberJoinedEvent event = objectMapper.treeToValue(envelope.getPayload(), MemberJoinedEvent.class);
                    marketFacade.syncMember(event.getMember());
                }

                case "MemberModifiedEvent" -> {
                    MemberModifiedEvent event = objectMapper.treeToValue(envelope.getPayload(), MemberModifiedEvent.class);
                    marketFacade.syncMember(event.getMember());
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

    @KafkaListener(topics = "market-events", groupId = "market-service")
    @Transactional(propagation = REQUIRES_NEW)
    public void marketEventHandle(String value) {

        try {
            DomainEventEnvelope envelope = objectMapper.readValue(value, DomainEventEnvelope.class);

            switch (envelope.getEventType()) {

                case "MarketMemberCreatedEvent" -> {
                    MarketMemberCreatedEvent event = objectMapper.treeToValue(envelope.getPayload(), MarketMemberCreatedEvent.class);
                    marketFacade.createCart(event.getMember());
                }

                case "MarketReadyInitEvent" -> {
                    marketDataInit.makeBaseProducts();
                    marketDataInit.makeBaseCartItems();
                    marketDataInit.makeBaseOrders();
                    marketDataInit.makeBasePaidOrders();
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

    @KafkaListener(topics = "cash-events", groupId = "market-service")
    @Transactional(propagation = REQUIRES_NEW)
    public void cashEventHandle(String value) {

        try {
            DomainEventEnvelope envelope = objectMapper.readValue(value, DomainEventEnvelope.class);

            switch (envelope.getEventType()) {

                case "CashOrderPaymentSuccessdedEvent" -> {
                    CashOrderPaymentSuccessdedEvent event = objectMapper.treeToValue(envelope.getPayload(), CashOrderPaymentSuccessdedEvent.class);
                    marketFacade.handle(event);
                }

                case "CashOrderPaymentFailedEvent" -> {
                    CashOrderPaymentFailedEvent event = objectMapper.treeToValue(envelope.getPayload(), CashOrderPaymentFailedEvent.class);
                    marketFacade.handle(event);
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
