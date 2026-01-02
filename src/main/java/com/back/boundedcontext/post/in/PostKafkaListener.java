package com.back.boundedcontext.post.in;

import com.back.boundedcontext.post.app.PostFacade;
import com.back.global.eventpublisher.topic.DomainEventEnvelope;
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
 * @date : 26. 1. 1.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PostKafkaListener {

    private final PostFacade postFacade;
    private final PostDataInit postDataInit;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "member-events", groupId = "post-service")
    @Transactional(propagation = REQUIRES_NEW)
    public void memberEventHandle(String value) {

        try {
            DomainEventEnvelope envelope =
                    objectMapper.readValue(value, DomainEventEnvelope.class);

            switch (envelope.getEventType()) {

                case "MemberJoinedEvent" -> {
                    MemberJoinedEvent event =
                            objectMapper.treeToValue(envelope.getPayload(), MemberJoinedEvent.class);

                    postFacade.syncMember(event.getMember());
                }

                case "MemberModifiedEvent" -> {
                    MemberModifiedEvent event =
                            objectMapper.treeToValue(envelope.getPayload(), MemberModifiedEvent.class);

                    postFacade.syncMember(event.getMember());
                }

                case "MemberInitCompletedEvent" -> {
                    postDataInit.makeBasePosts();
                    postDataInit.makeBasePostComments();
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
