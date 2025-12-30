package com.back.boundedcontext.payout.in;

import com.back.boundedcontext.payout.app.PayoutFacade;
import com.back.boundedcontext.payout.domain.PayoutPolicy;
import com.back.standard.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
@Configuration
@Slf4j
public class PayoutDataInit {

    private final PayoutDataInit self;
    private final PayoutFacade payoutFacade;
    private final JobOperator jobOperator;
    private final Job payoutCollectItemsAndCompletePayoutsJob;

    public PayoutDataInit(
            @Lazy PayoutDataInit self,
            PayoutFacade payoutFacade,
            JobOperator jobOperator,
            Job payoutCollectItemsAndCompletePayoutsJob
    ) {
        this.self = self;
        this.payoutFacade = payoutFacade;
        this.jobOperator = jobOperator;
        this.payoutCollectItemsAndCompletePayoutsJob = payoutCollectItemsAndCompletePayoutsJob;
    }

    @Bean
    @Order(4)
    public ApplicationRunner payoutDataInitApplicationRunner() {
        return args -> {
            self.forceMakePayoutReadyCandidatesItems();
            self.collectPayoutItemsMore();
            self.completePayoutsMore();
//            self.runCollectPayoutItemsAndCompletePayoutsBatchJob();
        };
    }

    @Transactional
    public void forceMakePayoutReadyCandidatesItems() {
        payoutFacade.findPayoutCandidateItems().forEach(item -> {
            Utils.reflection.setField(
                    item,
                    "paymentDate",
                    LocalDateTime.now().minusDays(PayoutPolicy.getPayoutReadyWaitingDays() + 1)
            );
        });
    }

    @Transactional
    public void collectPayoutItemsMore() {
        payoutFacade.collectPayoutItemsMore(6);
    }

    @Transactional
    public void completePayoutsMore() {
        payoutFacade.completePayoutsMore(6);
    }

    public void runCollectPayoutItemsAndCompletePayoutsBatchJob() {
        String runDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString(
                        "runDate", runDate
                )
                .toJobParameters();

        try {
            JobExecution execution = jobOperator.start(payoutCollectItemsAndCompletePayoutsJob, jobParameters);
        } catch (JobInstanceAlreadyCompleteException e) {
            log.error("Job instance already complete for today {}", runDate, e);
        } catch (JobExecutionAlreadyRunningException e) {
            log.error("Job execution already running", e);
        } catch (InvalidJobParametersException e) {
            log.error("Invalid job parameters", e);
        } catch (JobRestartException e) {
            log.error("job restart exception", e);
        }
    }
}
