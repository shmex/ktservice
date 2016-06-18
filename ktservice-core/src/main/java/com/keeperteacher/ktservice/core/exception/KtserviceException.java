package com.keeperteacher.ktservice.core.exception;

public class KtserviceException extends Exception {

    public KtserviceException(String message) {
        super(message);
    }

    public KtserviceException(String message, Exception e) {
        super(message, e);
    }

    public int getErrorCode() {
        return KtserviceErrorCode.SERVER_ERROR;
    }
}
