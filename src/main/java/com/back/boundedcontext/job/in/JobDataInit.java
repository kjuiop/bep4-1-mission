package com.back.boundedcontext.job.in;

import com.back.boundedcontext.job.app.JobFacade;
import lombok.extern.slf4j.Slf4j;
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
    private final boolean useKafkaEvent;

    public JobDataInit(
            @Lazy JobDataInit self,
            JobFacade jobFacade,
            boolean useKafkaEvent
    ) {
        this.self = self;
        this.jobFacade = jobFacade;
        this.useKafkaEvent = useKafkaEvent;
    }

    @Bean
    @Order(1)
    public ApplicationRunner jobDataInitApplicationRunner() {
        return args -> {
            if (!useKafkaEvent) {
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
