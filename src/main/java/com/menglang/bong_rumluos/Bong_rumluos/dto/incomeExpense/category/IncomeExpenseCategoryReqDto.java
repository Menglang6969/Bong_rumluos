package com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.category;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record IncomeExpenseCategoryReqDto(
        @NotNull
        @NotBlank(message = "Title Must be not Blank Or Blank")
        String title,
        String description) {

}
