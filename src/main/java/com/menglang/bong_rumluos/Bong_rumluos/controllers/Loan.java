package com.menglang.bong_rumluos.Bong_rumluos.controllers;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanDto;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanResponse;
import com.menglang.bong_rumluos.Bong_rumluos.services.laon.LoanService;
import com.menglang.bong_rumluos.Bong_rumluos.services.loanDetails.LoanDetailsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class Loan {
    private static final Logger log = LoggerFactory.getLogger(Loan.class);
    private final LoanService loanService;
    private final LoanDetailsService loanDetailsService;

    @PostMapping("/loans")
    public ResponseEntity<LoanResponse> create(@RequestBody LoanDto dto) {
        log.info("invoke make loan......");
        return ResponseEntity.ok(loanService.create(dto));
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<LoanResponse> view(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.view(id));
    }

    @GetMapping("/{cid}/loans")
    public ResponseEntity<List<LoanResponse>> getLoansByCustomerId(@PathVariable Long cid) {
        return ResponseEntity.ok(loanService.findByCustomerId(cid));
    }

    @PatchMapping("/{cid}/loans/loan-details/{id}")
    public ResponseEntity<LoanResponse> updateLoanDetails(@PathVariable Long cid, @PathVariable Long id) {
        return ResponseEntity.ok(loanDetailsService.updateLoanDetailsStatus(id));
    }

}
