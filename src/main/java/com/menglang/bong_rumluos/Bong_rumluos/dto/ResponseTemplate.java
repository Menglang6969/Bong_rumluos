package com.menglang.bong_rumluos.Bong_rumluos.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Builder
public record ResponseTemplate(
        LocalDateTime dateTime,
        String message,
        HttpStatus code,
        Object data
) {
}
