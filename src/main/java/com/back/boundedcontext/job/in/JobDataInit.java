package com.back.boundedcontext.job.in;

import com.back.boundedcontext.job.app.JobFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@ConditionalOnProperty(name = "app.data-init.enabled", havingValue = "true", matchIfMissing = true)
@Configuration
@Slf4j
public class JobDataInit {

    private final JobDataInit self;
    private final JobFacade jobFacade;

    @Value("${app.event-publisher.type}")
    private String publisherType;

    public JobDataInit(
            @Lazy JobDataInit self,
            JobFacade jobFacade
    ) {
        this.self = self;
        this.jobFacade = jobFacade;
    }

    @Bean
    @Order(1)
    public ApplicationRunner jobDataInitApplicationRunner() {
        return args -> {
            if (!publisherType.equalsIgnoreCase("kafka")) {
                return;
            }

            self.makeBaseDataInitJob();
        };
    }

    @Transactional
    public void makeBaseDataInitJob() {
        jobFacade.createInitDataJob("post-data-init", 1);
        jobFacade.createInitDataJob("cash-data-init", 2);
        jobFacade.createInitDataJob("market-data-init", 3);
        jobFacade.createInitDataJob("payout-data-init", 1);
    }
}
