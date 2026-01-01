package com.back.infra;

import com.back.standard.event.HasEventName;

/**
 * @author : JAKE
 * @date : 26. 1. 1.
 */
public record MyEvent(String msg) implements HasEventName {
}
