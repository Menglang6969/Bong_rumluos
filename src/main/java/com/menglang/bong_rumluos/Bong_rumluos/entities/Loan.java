package com.menglang.bong_rumluos.Bong_rumluos.entities;

import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseAuditEntity;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.Terms;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
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
    private Short rate;

    @Enumerated(EnumType.STRING)
    private Terms term;

    private BigDecimal principal;

    @Column(name = "total_interest")
    private BigDecimal totalInterest;
    private Short alert;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    @Column(name = "start_date",nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "loan",orphanRemoval = true,cascade = CascadeType.ALL)
    @Builder.Default
    private Set<LoanDetails> loanDetails=new HashSet<>();
}
