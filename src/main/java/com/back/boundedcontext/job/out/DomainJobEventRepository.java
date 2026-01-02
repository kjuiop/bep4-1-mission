package com.back.boundedcontext.job.out;

import com.back.boundedcontext.job.domain.DomainJobEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 26. 1. 2.
 */
public interface DomainJobEventRepository extends JpaRepository<DomainJobEvent, Long> {
    Optional<DomainJobEvent> findByJobName(String jobName);
}
