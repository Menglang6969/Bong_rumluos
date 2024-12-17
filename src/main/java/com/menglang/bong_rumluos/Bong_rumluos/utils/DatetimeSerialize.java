package com.menglang.bong_rumluos.Bong_rumluos.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatetimeSerialize extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String date = jsonParser.getText();
        // Handle "yyyy-MM-dd" and set time to midnight
        return LocalDateTime.parse(date + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
