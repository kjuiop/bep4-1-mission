package com.back.standard.event;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author : JAKE
 * @date : 26. 1. 1.
 */
public interface HasEventName {

    @JsonIgnore
    default String getEventName() {
        return this.getClass().getSimpleName();
    }
}
