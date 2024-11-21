package com.menglang.bong_rumluos.Bong_rumluos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Index;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BaseAuditDTO {

    @JsonProperty(index = 20)
    private LocalDate createdAt;
    @JsonProperty(index = 21)
    private String createdBy;
    @JsonProperty(index = 22)
    private LocalDate updatedAt;
    @JsonProperty(index = 23)
    private String updatedBy;
}
