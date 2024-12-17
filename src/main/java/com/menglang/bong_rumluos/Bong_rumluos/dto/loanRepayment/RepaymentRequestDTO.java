package com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record RepaymentRequestDTO(
        @NotNull
        @NotBlank(message = "loanDetail must be not null or blank")
        Long loanDetails,
        @NotNull
        LocalDate repaymentDate,
        @Positive(message = "amount must be positive number!")
        BigDecimal amountRepay,
        @Positive(message = "amount must be positive number!")
        Long penaltyRate,
        String noted
) {
}
