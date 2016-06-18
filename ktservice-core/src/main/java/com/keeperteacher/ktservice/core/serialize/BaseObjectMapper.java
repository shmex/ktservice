package com.keeperteacher.ktservice.core.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.keeperteacher.ktservice.core.exception.KtserviceException;
import com.keeperteacher.ktservice.core.exception.ValidationException;

public class BaseObjectMapper extends ObjectMapper {

    public BaseObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(KtserviceException.class, new KtserviceExceptionSerializer());
        simpleModule.addSerializer(ValidationException.class, new ValidationExceptionSerializer());
        this.registerModule(simpleModule);
    }

}
