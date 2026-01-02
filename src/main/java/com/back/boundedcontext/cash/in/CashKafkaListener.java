package com.back.boundedcontext.cash.in;

import com.back.boundedcontext.cash.app.CashFacade;
import com.back.global.eventpublisher.topic.DomainEventEnvelope;
import com.back.shared.member.event.CashMemberCreatedEvent;
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
public class CashKafkaListener {

    private final CashFacade cashFacade;
    private final CashDataInit cashDataInit;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "member-events", groupId = "cash-service")
    @Transactional(propagation = REQUIRES_NEW)
    public void memberEventHandle(String value) {

        try {
            DomainEventEnvelope envelope = objectMapper.readValue(value, DomainEventEnvelope.class);

            switch (envelope.getEventType()) {

                case "MemberJoinedEvent" -> {
                    MemberJoinedEvent event = objectMapper.treeToValue(envelope.getPayload(), MemberJoinedEvent.class);
                    cashFacade.syncMember(event.getMember());
                }

                case "MemberModifiedEvent" -> {
                    MemberModifiedEvent event = objectMapper.treeToValue(envelope.getPayload(), MemberModifiedEvent.class);
                    cashFacade.syncMember(event.getMember());
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

    @KafkaListener(topics = "cash-events", groupId = "cash-service")
    @Transactional(propagation = REQUIRES_NEW)
    public void cashEventHandle(String value) {

        try {
            DomainEventEnvelope envelope = objectMapper.readValue(value, DomainEventEnvelope.class);

            switch (envelope.getEventType()) {

                case "CashMemberCreatedEvent" -> {
                    CashMemberCreatedEvent event = objectMapper.treeToValue(envelope.getPayload(), CashMemberCreatedEvent.class);
                    cashFacade.createWallet(event.getMember());
                }

                case "CashReadyInitEvent" -> {
                    cashDataInit.makeBaseCredits();
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
