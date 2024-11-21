package com.menglang.bong_rumluos.Bong_rumluos.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.menglang.bong_rumluos.Bong_rumluos.dto.BaseAuditDTO;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CategoryResponse extends BaseAuditDTO {
    @JsonProperty(index = 1)
    private Long id;
    @JsonProperty(index = 2)
    private String name;
    @JsonProperty(index = 3)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @JsonProperty(index = 4)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String color;
    @JsonProperty(index = 5)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CategoryResponse parent;


    public CategoryResponse(LocalDate createdAt, String createdBy, LocalDate updatedAt, String updatedBy) {
        super(createdAt, createdBy, updatedAt, updatedBy);
    }
}
