package com.qburst.serialization;

import java.io.IOException;
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LocalTimeDeserializer extends StdDeserializer<LocalTime> {

    private static final long serialVersionUID = 1L;

    protected LocalTimeDeserializer() {
        super(LocalTime.class);
    }


    @Override
    public LocalTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return LocalTime.parse(jp.readValueAs(String.class));
    }

}