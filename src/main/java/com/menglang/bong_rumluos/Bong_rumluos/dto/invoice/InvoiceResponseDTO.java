package com.menglang.bong_rumluos.Bong_rumluos.dto.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.menglang.bong_rumluos.Bong_rumluos.dto.BaseAuditDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerBaseResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@JsonPropertyOrder({"id", "invoiceNo", "customer", "totalAmount", "totalPenalty", "totalPayment", "description", "loanRepayments", "createdBy", "createAt", "updatedBy", "updatedAt"})
public class InvoiceResponseDTO extends BaseAuditDTO {

    private String invoiceNo;
    private CustomerBaseResponse customer;
    private BigDecimal totalAmount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RepaymentResponseDTO> loanRepayments;
    private BigDecimal totalPenalty;
    private BigDecimal totalPayment;
    private String description;

    public InvoiceResponseDTO(Long id, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(id, createdAt, createdBy, updatedAt, updatedBy);
    }
}
