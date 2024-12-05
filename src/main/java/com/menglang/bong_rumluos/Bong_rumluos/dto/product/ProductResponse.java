package com.menglang.bong_rumluos.Bong_rumluos.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.menglang.bong_rumluos.Bong_rumluos.dto.BaseAuditDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryBaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonPropertyOrder({"id", "name", "description", "imageUrl", "color", "category", "identify1", "identify2", "identify3", "identify4", "createdAt", "createdBy", "updatedAt", "updatedBy"})
public class ProductResponse extends BaseAuditDTO {
    private String name;
    private String description;
    private String imageUrl;
    private String color;
    private CategoryBaseResponse category;
    private String identify1;
    private String identify2;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String identify3;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String identify4;

    public ProductResponse(Long id, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(id, createdAt, createdBy, updatedAt, updatedBy);
    }
}
