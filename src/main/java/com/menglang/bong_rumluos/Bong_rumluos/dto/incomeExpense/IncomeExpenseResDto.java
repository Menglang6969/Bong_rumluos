package com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.menglang.bong_rumluos.Bong_rumluos.dto.BaseAuditDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.category.IncomeExpenseCategoryResDto;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.IncomeExpenseType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonPropertyOrder({"id","category","type","amount","description"})
public class IncomeExpenseResDto extends BaseAuditDTO {
    private IncomeExpenseCategoryResDto category;
    private IncomeExpenseType type;
    private BigDecimal amount;
    private String description;

    public IncomeExpenseResDto(Long id, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(id, createdAt, createdBy, updatedAt, updatedBy);
    }


}
