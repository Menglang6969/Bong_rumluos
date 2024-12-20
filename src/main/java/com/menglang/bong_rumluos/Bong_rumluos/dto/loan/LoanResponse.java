package com.menglang.bong_rumluos.Bong_rumluos.dto.loan;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.menglang.bong_rumluos.Bong_rumluos.dto.BaseAuditDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerBaseResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.loanDetails.LoanDetailsResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.product.ProductBaseResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.Terms;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@JsonPropertyOrder({"id", "loanKey", "totalAmount", "principal", "totalInterest","deposit", "rate","penaltyRate", "term", "startDate", "endDate", "alert", "customer","products" ,"loanStatus", "loanDetails", "createdBy", "createAt", "updatedBy", "updatedAt"})
public class LoanResponse extends BaseAuditDTO {
    String loanKey;
    BigDecimal totalAmount;
    BigDecimal principal;
    BigDecimal totalInterest;
    double rate;
    Terms term;
    LocalDate startDate;
    LocalDate endDate;
    CustomerBaseResponse customer;
    List<ProductBaseResponse> products;
    LoanStatus loanStatus;
    BigDecimal deposit;
    List<LoanDetailsResponse> loanDetails;

    public LoanResponse(Long id, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(id, createdAt, createdBy, updatedAt, updatedBy);
    }
}
