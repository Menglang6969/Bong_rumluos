package com.menglang.bong_rumluos.Bong_rumluos.dto.loan;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EMIResponse {
    int month;
    double emi;
    double principalPayment;
    double interestPayment;
    double outstandingBalance;
}
