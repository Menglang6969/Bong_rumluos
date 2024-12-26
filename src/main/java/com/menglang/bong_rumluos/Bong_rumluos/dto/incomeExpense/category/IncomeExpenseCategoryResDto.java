package com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.category;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.menglang.bong_rumluos.Bong_rumluos.dto.BaseAuditDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@JsonPropertyOrder({"id","title","description","createdAt","createdBy","updatedAt","updatedBy"})
public class IncomeExpenseCategoryResDto extends BaseAuditDTO {
    private String title;
    private String description;

    public IncomeExpenseCategoryResDto(Long id, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(id, createdAt, createdBy, updatedAt, updatedBy);
    }
}
