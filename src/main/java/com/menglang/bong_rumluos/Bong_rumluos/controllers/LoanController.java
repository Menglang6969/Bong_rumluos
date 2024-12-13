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
public class LoanController {
    private static final Logger log = LoggerFactory.getLogger(LoanController.class);
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

    @PatchMapping("/loans/loan-details/{id}")
    public ResponseEntity<LoanResponse> updateLoanDetails(@PathVariable Long id) {
        return ResponseEntity.ok(loanDetailsService.updateLoanDetailsStatus(id));
    }

    @GetMapping("/loans/all")
    public ResponseEntity<List<LoanResponse>> getAllLoans(
            @RequestParam(name = "page",defaultValue = "1") int page,
            @RequestParam(name = "limit",defaultValue = "10") short limit,
            @RequestParam(name = "order-by",defaultValue = "loanKey") String orderBy,
            @RequestParam(name = "sort-by",defaultValue = "ASC") String sortBy,
            @RequestParam(name = "query",defaultValue = "")String query
    ){
        return ResponseEntity.ok(loanService.findAll(page,limit,orderBy,sortBy,query));
    }

}
