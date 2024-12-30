package com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Setter
@Getter
public class PageBody implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object body;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PageMetadata pageMetadata;
}
