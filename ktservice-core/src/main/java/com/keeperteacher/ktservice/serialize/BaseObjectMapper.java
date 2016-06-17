package com.keeperteacher.ktservice.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.keeperteacher.ktservice.exception.KtserviceException;
import com.keeperteacher.ktservice.exception.ValidationException;

public class BaseObjectMapper extends ObjectMapper {

    public BaseObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(KtserviceException.class, new KtserviceExceptionSerializer());
        simpleModule.addSerializer(ValidationException.class, new ValidationExceptionSerializer());
        this.registerModule(simpleModule);
    }

}
