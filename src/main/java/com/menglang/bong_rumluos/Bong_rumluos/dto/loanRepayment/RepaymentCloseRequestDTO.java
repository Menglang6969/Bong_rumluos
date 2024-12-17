package com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RepaymentCloseRequestDTO(
        Long loan,
        BigDecimal totalAmount,
        Long penalty,
        String noted
) {
}
