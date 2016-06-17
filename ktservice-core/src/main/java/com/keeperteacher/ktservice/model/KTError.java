package com.keeperteacher.ktservice.model;

public class KTError extends Exception {
    public KTError() {
    }

    public KTError(String message) {
        super(message);
    }

    public KTError(String message, Throwable cause) {
        super(message, cause);
    }

    public KTError(Throwable cause) {
        super(cause);
    }

    public KTError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
