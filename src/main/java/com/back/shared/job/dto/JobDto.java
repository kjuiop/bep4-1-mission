package com.back.shared.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 26. 1. 2.
 */
@AllArgsConstructor
@Getter
@Builder
public class JobDto {

    private final long id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final String jobName;
    private final boolean ready;
    private final int satisfiedConditions;
    private final int requiredConditions;
    private final LocalDateTime completeAt;

    public static JobDto readyByPostMember() {
        return JobDto.builder()
                .jobName("post-data-init")
                .satisfiedConditions(1)
                .build();
    }

    public static JobDto readyByCashMember() {
        return JobDto.builder()
                .jobName("cash-data-init")
                .satisfiedConditions(1)
                .build();
    }

    public static JobDto readyByCashWallet() {
        return JobDto.builder()
                .jobName("cash-data-init")
                .satisfiedConditions(1)
                .build();
    }

    public static JobDto readyByMarketMemberForMarket() {
        return JobDto.builder()
                .jobName("market-data-init")
                .satisfiedConditions(1)
                .build();
    }

    public static JobDto readyByPostForMarket() {
        return JobDto.builder()
                .jobName("market-data-init")
                .satisfiedConditions(1)
                .build();
    }

    public static JobDto readyByCartForMarket() {
        return JobDto.builder()
                .jobName("market-data-init")
                .satisfiedConditions(1)
                .build();
    }
}
