package com.menglang.bong_rumluos.Bong_rumluos.dto.loan;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanSchedulerResponse {
    Long id;
    LocalDate repaymentDate;
    BigDecimal principalPayment;
    BigDecimal interestCap;
    BigDecimal interestPayment;
    BigDecimal outstandingBalance;
}
