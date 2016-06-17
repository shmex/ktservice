package com.keeperteacher.ktservice.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.keeperteacher.ktservice.exception.KtserviceException;

import java.io.IOException;

public class KtserviceExceptionSerializer extends JsonSerializer<KtserviceException> {

    @Override
    public void serialize(KtserviceException e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("message", e.getMessage());
        jsonGenerator.writeNumberField("errorCode", e.getErrorCode());

        jsonGenerator.writeEndObject();
    }

}
