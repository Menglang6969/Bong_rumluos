package com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse.page.PageBody;
import com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse.page.PageMetadata;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Builder
@Data
public class BaseResponse implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StatusResponse status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PageBody content;


    public static ResponseEntity<BaseResponse> failed(String message, HttpStatusCode status) {
        StatusResponse statusResponse = new StatusResponse((short) status.value(), LocalDateTime.now(), message);
        BaseResponse baseResponse = BaseResponse.builder()
                .success(false)
                .status(statusResponse)
                .build();
        return ResponseEntity.status(status).body(baseResponse);
    }


    public static ResponseEntity<BaseResponse> success(Object data, Page<?> page, String message) {

        StatusResponse status = StatusResponse.builder()
                .timeStamp(LocalDateTime.now())
                .code((short) 200)
                .message(message)
                .build();

        PageBody body=PageBody.builder().build();

        if (data instanceof BasePageResponse){
            body.setBody(data);
        } else if (data instanceof List<?>) {
            if (!((List<?>) data).isEmpty() && ((List<?>) data).get(0) instanceof BasePageResponse) {
                body.setBody(data);
            }else {
                body.setBody(Collections.emptyList());
            }
        }

        if(page!=null) {
            PageMetadata metadata=PageMetadata.builder()
                    .totalElement(page.getTotalElements())
                    .totalPage(page.getTotalPages())
                    .size(page.getSize())
                    .page(page.getNumber()+1)
                    .hasNext(page.hasNext())
                    .hasPrevious(page.hasPrevious())
                    .build();
            body.setPageMetadata(metadata);
        }

        BaseResponse response=BaseResponse.builder()
                .status(status)
                .content(body)
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }


}
