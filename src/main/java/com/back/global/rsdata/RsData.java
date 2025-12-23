package com.back.global.rsdata;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@AllArgsConstructor
@Getter
public class RsData<T> {
    private final String resultCode;
    private final String msg;
    private final T data;

    public RsData(String resultCode, String msg) {
        this(resultCode, msg, null);
    }
}
