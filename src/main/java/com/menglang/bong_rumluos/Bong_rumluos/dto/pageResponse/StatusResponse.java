package com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
public class StatusResponse implements Serializable {
    private Short code;
    private LocalDateTime timeStamp;
    private String message;
}
