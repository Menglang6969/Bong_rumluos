package com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense;

import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.IncomeExpenseType;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record IncomeExpenseReqDto(
        IncomeExpenseType type,
        Long category,
        BigDecimal amount,
        String description
) {
}
