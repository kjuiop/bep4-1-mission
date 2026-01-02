package com.back.boundedcontext.job.domain;

import com.back.global.jpa.entity.BaseManualIdAndTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 26. 1. 2.
 */
@Entity
@Table(name = "DOMAIN_JOB_EVENT")
@NoArgsConstructor
@Getter
public class DomainJobEvent extends BaseManualIdAndTime {

    private String jobName;

    private boolean ready = false;

    private int satisfiedConditions;

    private int requiredConditions;

    private LocalDateTime completeAt;

    public DomainJobEvent(String jobName, int requiredConditions) {
        this.jobName = jobName;
        this.requiredConditions = requiredConditions;
    }

    public void updatePostInitEvent(int satisfiedConditions) {
        this.satisfiedConditions +=  satisfiedConditions;
    }

    public boolean checkIsReady() {
        return requiredConditions == satisfiedConditions;
    }

    public void updateReadyStatus() {
        this.ready = true;
        this.completeAt = LocalDateTime.now();
    }
}
