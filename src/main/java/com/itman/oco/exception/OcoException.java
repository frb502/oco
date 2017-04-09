package com.itman.oco.exception;

/**
 * Created by furongbin on 17/4/9.
 */
public class OcoException extends RuntimeException {
    public OcoException() {
        super();
    }

    public OcoException(String message) {
        super(message);
    }

    public OcoException(String message, Throwable cause) {
        super(message, cause);
    }
}
