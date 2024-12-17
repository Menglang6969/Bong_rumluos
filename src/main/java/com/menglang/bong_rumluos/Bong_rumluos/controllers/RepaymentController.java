package com.menglang.bong_rumluos.Bong_rumluos.controllers;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentRequestDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loanRepayment.RepaymentResponseDTO;
import com.menglang.bong_rumluos.Bong_rumluos.services.loanRepayment.LoanRepaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/payments")
@RestController
@RequiredArgsConstructor
public class RepaymentController {
    private static final Logger log = LoggerFactory.getLogger(RepaymentController.class);
    private final LoanRepaymentService loanRepaymentService;

    @PostMapping
    public ResponseEntity<RepaymentResponseDTO> makeRepayment(@RequestBody RepaymentRequestDTO dto) {
        log.info("invoke repayment ...");
        return ResponseEntity.ok(loanRepaymentService.makeRepayment(dto));
    }
}
