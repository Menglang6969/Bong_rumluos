package com.menglang.bong_rumluos.Bong_rumluos.dto.invoice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record InvoiceHalfPayRequestDTO(
        @NotNull
        @NotBlank(message = "customer must be not null or Blank")
        String invoiceNo,
        Long customer,
        BigDecimal totalAmount,
        BigDecimal totalPenalty,
        String description
) {

}
