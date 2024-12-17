package com.menglang.bong_rumluos.Bong_rumluos.entities;

import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "invoices", indexes = {@Index(name = "idx_invoiceNo", columnList = "invoiceNo")})
public class Invoice extends BaseAuditEntity<Long> {

    @Column(name = "invoice_no", length = 20)
    private String invoiceNo;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "invoiceId",orphanRemoval = true,cascade = CascadeType.ALL)
    @Builder.Default
    private Set<LoanRepayment> loanRepayments = new HashSet<>();

    @Column(name = "total_penalty")
    private BigDecimal totalPenalty;

    @Transient
    private BigDecimal totalPayment;

    @Column(length = 100)
    private String description;
}
