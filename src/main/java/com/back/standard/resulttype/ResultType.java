package com.back.standard.resulttype;

/**
 * @author : JAKE
 * @date : 25. 12. 29.
 */
public interface ResultType {

    String getResultCode();

    String getMsg();

    default <T> T getData() {
        return null;
    }
}
