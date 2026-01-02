package com.back.boundedcontext.job.in;

import com.back.boundedcontext.job.app.JobFacade;
import com.back.global.eventpublisher.topic.DomainEventEnvelope;
import com.back.shared.job.event.JobReadyInitEvent;
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
public class JobKafkaListener {

    private final JobFacade jobFacade;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "job-events", groupId = "job-service")
    @Transactional(propagation = REQUIRES_NEW)
    public void postEventHandle(String value) {

        try {
            DomainEventEnvelope envelope = objectMapper.readValue(value, DomainEventEnvelope.class);

            switch (envelope.getEventType()) {

                case "JobReadyInitEvent" -> {

                    JobReadyInitEvent event = objectMapper.treeToValue(envelope.getPayload(), JobReadyInitEvent.class);
                    jobFacade.updateJobInitEvent(event.getJob().getJobName(), event.getJob().getSatisfiedConditions());
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
