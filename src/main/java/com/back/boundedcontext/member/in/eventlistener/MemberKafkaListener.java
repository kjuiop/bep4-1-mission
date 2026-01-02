package com.back.boundedcontext.member.in.eventlistener;

import com.back.boundedcontext.member.app.MemberFacade;
import com.back.boundedcontext.member.domain.Member;
import com.back.global.eventpublisher.topic.DomainEventEnvelope;
import com.back.shared.post.event.PostCommentCreatedEvent;
import com.back.shared.post.event.PostCreatedEvent;
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
public class MemberKafkaListener {

    private final MemberFacade memberFacade;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "post-events", groupId = "member-service")
    @Transactional(propagation = REQUIRES_NEW)
    public void postEventHandle(String value) {

        try {
            DomainEventEnvelope envelope =
                    objectMapper.readValue(value, DomainEventEnvelope.class);

            switch (envelope.getEventType()) {

                case "PostCreatedEvent" -> {
                    PostCreatedEvent event =
                            objectMapper.treeToValue(envelope.getPayload(), PostCreatedEvent.class);

                    Member member = memberFacade.findById(event.getPost().getAuthorId()).get();
                    member.increaseActivityScore(3);
                }

                case "PostCommentCreatedEvent" -> {
                    PostCommentCreatedEvent event =
                            objectMapper.treeToValue(envelope.getPayload(), PostCommentCreatedEvent.class);

                    Member member = memberFacade.findById(event.getPostComment().getAuthorId()).get();
                    member.increaseActivityScore(1);
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
