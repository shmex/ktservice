package com.keeperteacher.ktservice.core.exception;

public class ResourceNotFoundException extends KtserviceException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Exception e) {
        super(message, e);
    }

    @Override
    public int getErrorCode() {
        return KtserviceErrorCode.RESOURCE_NOT_FOUND;
    }
}
