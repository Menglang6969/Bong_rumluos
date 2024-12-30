package com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse.page;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class PageMetadata implements Serializable {
    private Long totalElement;
    private int page;
    private int size;
    private int totalPage;
    private boolean hasPrevious;
    private boolean hasNext;

}
