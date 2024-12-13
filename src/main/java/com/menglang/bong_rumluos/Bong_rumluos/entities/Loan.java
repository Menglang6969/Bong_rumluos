package com.menglang.bong_rumluos.Bong_rumluos.entities;

import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseAuditEntity;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.Terms;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loans",
        indexes = {
                @Index(name = "idx_loan_key", columnList = "loanKey")
        }
)
public class Loan extends BaseAuditEntity<Long> {

    @Column(name = "loan_key",unique = true)
    private String loanKey;

    @Column(name = "total_amount")
    @Transient
    private BigDecimal totalAmount;
    private short rate;

    @Enumerated(EnumType.STRING)
    private Terms term;

    private BigDecimal principal;

    @Column(name = "total_interest")
    private BigDecimal totalInterest;
    private short alert;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    @Column(name = "start_date",nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @Column(name = "penalty_rate", precision = 5, scale = 2)
    private short penaltyRate;

    @OneToMany(mappedBy = "loan",orphanRemoval = true,cascade = CascadeType.ALL)
    @Builder.Default
    private Set<LoanDetails> loanDetails=new HashSet<>();

    public BigDecimal getTotalAmount() {
        if (principal != null && totalInterest != null) {
            return principal.add(totalInterest);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Loan loan = (Loan) object;
        return rate == loan.rate && alert == loan.alert && Objects.equals(loanKey, loan.loanKey) && Objects.equals(totalAmount, loan.totalAmount) && term == loan.term && Objects.equals(principal, loan.principal) && Objects.equals(totalInterest, loan.totalInterest) && loanStatus == loan.loanStatus && Objects.equals(startDate, loan.startDate) && Objects.equals(endDate, loan.endDate) && Objects.equals(customer, loan.customer) && Objects.equals(loanDetails, loan.loanDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanKey, totalAmount, rate, term, principal, totalInterest, alert, loanStatus, startDate, endDate, customer, loanDetails);
    }
}

