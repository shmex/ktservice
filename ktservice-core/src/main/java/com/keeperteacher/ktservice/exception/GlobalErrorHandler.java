package com.keeperteacher.ktservice.exception;

import com.keeperteacher.ktservice.model.KTError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    public KTError handle(Exception e) {
        return new KTError(e);
    }
}
