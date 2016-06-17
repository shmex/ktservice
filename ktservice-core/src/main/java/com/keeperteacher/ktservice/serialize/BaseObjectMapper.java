package com.keeperteacher.ktservice.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.keeperteacher.ktservice.exception.KtserviceException;

public class BaseObjectMapper extends ObjectMapper {

    public BaseObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(KtserviceException.class, new KtserviceExceptionSerializer());
        this.registerModule(simpleModule);
    }

}
