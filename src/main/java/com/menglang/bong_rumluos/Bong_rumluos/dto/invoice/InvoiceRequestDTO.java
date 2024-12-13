package com.menglang.bong_rumluos.Bong_rumluos.dto.invoice;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record InvoiceRequestDTO(
        @NotNull
        @NotBlank(message = "customer must be not null or Blank")
        Long customer,

        @NotNull
        List<RepaymentRequestDTO> loanRepayments,

        String description
) {
}
