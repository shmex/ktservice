package com.keeperteacher.ktservice.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.keeperteacher.ktservice.exception.KtserviceException;

import java.io.IOException;

public class KtserviceExceptionSerializer extends JsonSerializer<KtserviceException> {

    public static void writeKtserviceExceptionFields(KtserviceException e, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStringField("message", e.getMessage());
        jsonGenerator.writeNumberField("errorCode", e.getErrorCode());
    }

    @Override
    public void serialize(KtserviceException e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();

        writeKtserviceExceptionFields(e, jsonGenerator);

        jsonGenerator.writeEndObject();
    }

}
