package com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
public class BaseResponse implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StatusResponse status;

    public static ResponseEntity<BaseResponse> failed(String message, HttpStatusCode status){
        StatusResponse statusResponse=new StatusResponse((short) status.value(), LocalDateTime.now(),message);
        BaseResponse baseResponse=BaseResponse.builder()
                .success(false)
                .status(statusResponse)
                .build();
        return ResponseEntity.status(status).body(baseResponse);
    }

}
