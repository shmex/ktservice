package com.keeperteacher.ktservice.core.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;

public class ValidationException extends KtserviceException {

    public final MethodArgumentNotValidException methodArgumentNotValidException;

    public ValidationException(MethodArgumentNotValidException e) {
        super("Validation failed for object: " + e.getBindingResult().getObjectName());
        this.methodArgumentNotValidException = e;
    }

    @Override
    public int getErrorCode() {
        return KtserviceErrorCode.VALIDATION_ERROR;
    }
}
