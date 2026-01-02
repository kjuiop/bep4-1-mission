package com.back.global.eventpublisher.topic;

import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSuccessdedEvent;
import com.back.shared.market.event.MarketOrderPaymentCompletedEvent;
import com.back.shared.market.event.MarketOrderPaymentRequestedEvent;
import com.back.shared.market.event.MarketOrderRequestPaymentStartedEvent;
import com.back.shared.member.event.CashMemberCreatedEvent;
import com.back.shared.member.event.MarketMemberCreatedEvent;
import com.back.shared.member.event.MemberJoinedEvent;
import com.back.shared.member.event.MemberModifiedEvent;
import com.back.shared.payout.event.PayoutCompletedEvent;
import com.back.shared.payout.event.PayoutMemberCreatedEvent;
import com.back.shared.post.event.PostCommentCreatedEvent;
import com.back.shared.post.event.PostCreatedEvent;
import com.back.shared.post.event.PostInitCompletedEvent;
import com.back.shared.post.event.PostReadyInitEvent;
import org.springframework.stereotype.Component;

/**
  * @author : JAKE
  * @date : 26. 1. 1.
*/
@Component
public class KafkaResolver {

    private static String MEMBER_EVENTS_TOPIC = "member-events";
    private static String CASH_EVENTS_TOPIC = "cash-events";
    private static String MARKET_EVENTS_TOPIC = "market-events";
    private static String PAYOUT_EVENTS_TOPIC = "payout-events";
    private static String POST_EVENT_TOPIC = "post-events";

    public String resolveTopic(Object event) {

        if (event instanceof MemberJoinedEvent) {
            return MEMBER_EVENTS_TOPIC;
        } else if (event instanceof MemberModifiedEvent) {
            return MEMBER_EVENTS_TOPIC;
        } else if (event instanceof CashMemberCreatedEvent) {
            return MEMBER_EVENTS_TOPIC;
        } else if (event instanceof MarketMemberCreatedEvent) {
            return MEMBER_EVENTS_TOPIC;
        } else if (event instanceof CashOrderPaymentSuccessdedEvent) {
            return CASH_EVENTS_TOPIC;
        } else if (event instanceof CashOrderPaymentFailedEvent) {
            return CASH_EVENTS_TOPIC;
        } else if (event instanceof MarketOrderRequestPaymentStartedEvent) {
            return MARKET_EVENTS_TOPIC;
        } else if (event instanceof MarketOrderPaymentRequestedEvent) {
            return MARKET_EVENTS_TOPIC;
        } else if (event instanceof MarketOrderPaymentCompletedEvent) {
            return MARKET_EVENTS_TOPIC;
        } else if (event instanceof PayoutMemberCreatedEvent) {
            return PAYOUT_EVENTS_TOPIC;
        } else if (event instanceof PayoutCompletedEvent) {
            return PAYOUT_EVENTS_TOPIC;
        } else if (event instanceof PostCreatedEvent) {
            return POST_EVENT_TOPIC;
        } else if (event instanceof PostCommentCreatedEvent) {
            return POST_EVENT_TOPIC;
        } else if (event instanceof PostReadyInitEvent) {
            return POST_EVENT_TOPIC;
        }

        return "unexpected-events";
    }

    public String resolveKey(Object event) {
        if (event instanceof MemberJoinedEvent e) return String.valueOf(e.getMember().getId());
        if (event instanceof MemberModifiedEvent e) return String.valueOf(e.getMember().getId());
        if (event instanceof CashMemberCreatedEvent e) return String.valueOf(e.getMember().getId());
        if (event instanceof MarketMemberCreatedEvent e) return String.valueOf(e.getMember().getId());
        if (event instanceof CashOrderPaymentSuccessdedEvent e) return String.valueOf(e.getOrder().getId());
        if (event instanceof CashOrderPaymentFailedEvent e) return String.valueOf(e.getOrder().getId());
        if (event instanceof MarketOrderRequestPaymentStartedEvent e) return String.valueOf(e.getOrder().getId());
        if (event instanceof MarketOrderPaymentRequestedEvent e) return String.valueOf(e.getOrder().getId());
        if (event instanceof MarketOrderPaymentCompletedEvent e) return String.valueOf(e.getOrder().getId());
        if (event instanceof PayoutMemberCreatedEvent e) return String.valueOf(e.getMember().getId());
        if (event instanceof PayoutCompletedEvent e) return String.valueOf(e.getPayout().getId());
        if (event instanceof PostCreatedEvent e) return String.valueOf(e.getPost().getId());
        if (event instanceof PostCommentCreatedEvent e) return String.valueOf(e.getPostComment().getPostId());
        if (event instanceof PostInitCompletedEvent e) return "post-init-completed";
        return null;
    }
}