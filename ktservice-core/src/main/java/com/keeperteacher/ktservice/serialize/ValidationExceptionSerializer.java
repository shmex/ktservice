package com.keeperteacher.ktservice.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.keeperteacher.ktservice.exception.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.IOException;

public class ValidationExceptionSerializer extends JsonSerializer<ValidationException> {

    @Override
    public void serialize(ValidationException e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();

        KtserviceExceptionSerializer.writeKtserviceExceptionFields(e, jsonGenerator);

        BindingResult bindingResult = e.methodArgumentNotValidException.getBindingResult();
        jsonGenerator.writeArrayFieldStart("errors");
        for(FieldError fieldError : bindingResult.getFieldErrors()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("field", fieldError.getField());
            jsonGenerator.writeStringField("message", fieldError.getDefaultMessage());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }

}
