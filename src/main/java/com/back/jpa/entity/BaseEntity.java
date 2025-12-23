package com.back.jpa.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    public String getModelTypeCode() {
        return this.getClass().getSimpleName();
    }
}
