package com.back.shared.job.event;

import com.back.shared.job.dto.JobDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 26. 1. 2.
 */
@Getter
@AllArgsConstructor
public class JobReadyInitEvent {
    private final JobDto job;
}
