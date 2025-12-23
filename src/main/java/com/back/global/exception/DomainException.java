package com.back.global.exception;

import lombok.Getter;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@Getter
public class DomainException extends RuntimeException {

    private final String resultCode;
    private final String msg;

    public DomainException(String resultCode, String msg) {
        super(resultCode + " : " + msg);
        this.resultCode = resultCode;
        this.msg = msg;
    }
}
