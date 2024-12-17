package com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class RepaymentResponseDTO {
    private String invoiceNo;
    private LocalDate repaymentDate;
    private BigDecimal amountRepay;
    private BigDecimal penalty;
    private String noted;
}
