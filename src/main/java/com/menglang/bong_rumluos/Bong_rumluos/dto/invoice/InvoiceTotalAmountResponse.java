package com.menglang.bong_rumluos.Bong_rumluos.dto.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceTotalAmountResponse {
    private BigDecimal totalPayment;
    private BigDecimal totalPenalty;
    private BigDecimal totalAmount;

}
