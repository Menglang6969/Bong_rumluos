package com.menglang.bong_rumluos.Bong_rumluos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name ="sequence_number" )
public class SequenceNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number",unique = true)
    private BigDecimal invoiceNumber;

    @Column(name = "loan_number",unique = true)
    private BigDecimal LoanNumber;
}
