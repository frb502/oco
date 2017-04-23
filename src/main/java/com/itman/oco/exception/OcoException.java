package com.itman.oco.exception;

/**
 * Created by furongbin on 17/4/9.
 */
public class OcoException extends RuntimeException {
    private int errorCode = 1;

    public OcoException() {
        super();
    }

    public OcoException(String message) {
        super(message);
    }

    public OcoException(String message, Throwable cause) {
        super(message, cause);
    }

    public OcoException(String msg, int errorCode) {
        this(msg);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
