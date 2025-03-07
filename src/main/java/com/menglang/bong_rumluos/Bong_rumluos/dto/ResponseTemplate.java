package com.menglang.bong_rumluos.Bong_rumluos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ResponseTemplate(
//        @JsonSerialize(using = LocalDateSerializer.class)
//        @JsonDeserialize(using = LocalDateDeserializer.class)
//        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dateTime,
        String message,
        short code,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Object data
) {
}
