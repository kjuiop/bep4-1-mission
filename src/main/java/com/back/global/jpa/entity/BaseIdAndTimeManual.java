package com.back.global.jpa.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@MappedSuperclass
@Getter
public abstract class BaseIdAndTimeManual extends BaseEntity {
    @Id
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
