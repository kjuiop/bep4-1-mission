package com.back.boundedcontext.job.app;

import com.back.boundedcontext.job.domain.DomainJobEvent;
import com.back.boundedcontext.job.out.DomainJobEventRepository;
import com.back.global.eventpublisher.DomainEventPublisher;
import com.back.shared.cash.event.CashReadyInitEvent;
import com.back.shared.market.event.MarketReadyInitEvent;
import com.back.shared.payout.event.PayoutReadyInitEvent;
import com.back.shared.post.event.PostReadyInitEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 26. 1. 2.
 */
@Service
@RequiredArgsConstructor
public class JobFacade {

    private final DomainJobEventRepository jobRepository;
    private final DomainEventPublisher eventPublisher;

    @Transactional
    public void createInitDataJob(String eventName, int requiredConditions) {
        jobRepository.save(new DomainJobEvent(eventName, requiredConditions));
    }

    @Transactional
    public void updateJobInitEvent(String jobName, int satisfiedConditions) {
        DomainJobEvent jobEvent = jobRepository.findByJobName(jobName).get();
        jobEvent.updatePostInitEvent(satisfiedConditions);
        boolean isReady = jobEvent.checkIsReady();
        if (isReady) {
            jobEvent.updateReadyStatus();
            publishReadyDataInit(jobEvent.getJobName());
        }
    }

    private void publishReadyDataInit(String jobName) {

        switch (jobName) {
            case "post-data-init" -> {
                eventPublisher.publish(new PostReadyInitEvent());
            }
            case "cash-data-init" -> {
                eventPublisher.publish(new CashReadyInitEvent());
            }
            case "market-data-init" -> {
                eventPublisher.publish(new MarketReadyInitEvent());
            }
            case "payout-data-init" -> {
                eventPublisher.publish(new PayoutReadyInitEvent());
            }
            default -> {
            }
        }
    }
}
