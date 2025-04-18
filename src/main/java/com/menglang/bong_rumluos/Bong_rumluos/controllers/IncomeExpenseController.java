package com.menglang.bong_rumluos.Bong_rumluos.controllers;

import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.IncomeExpenseMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.IncomeExpenseReqDto;
import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.IncomeExpenseResDto;
import com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse.BaseResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.IncomeExpense;
import com.menglang.bong_rumluos.Bong_rumluos.services.incomeExpense.IncomeExpenseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense_income")
@RequiredArgsConstructor
public class IncomeExpenseController {
    private static final Logger log = LoggerFactory.getLogger(IncomeExpenseController.class);
    private final IncomeExpenseService incomeExpenseService;
    private final IncomeExpenseMapper incomeExpenseMapper;

    @PostMapping
    public ResponseEntity<IncomeExpenseResDto> create(@RequestBody IncomeExpenseReqDto dto) {
        log.info("data request: {}", dto.type());
        return ResponseEntity.ok(incomeExpenseService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeExpenseResDto> update(@PathVariable("id") Long id, @RequestBody IncomeExpenseReqDto dto) {
        log.info("data request: {}", dto.category());
        return ResponseEntity.ok(incomeExpenseService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IncomeExpenseResDto> delete(@PathVariable("id") Long id) {
        log.info("data request: {}", id);
        return ResponseEntity.ok(incomeExpenseService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeExpenseResDto> getById(@PathVariable("id") Long id) {
        log.info("data request: {}", id);
        return ResponseEntity.ok(incomeExpenseService.view(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse> getById(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "shortBy", defaultValue = "description") String shortBy,
            @RequestParam(name = "orderBy", defaultValue = "DESC") String orderBy,
            @RequestParam(name = "query", defaultValue = "") String query
    ) {
        log.info("data request: ");
        Page<IncomeExpense> expensesIncomePage = incomeExpenseService.getAll(
                page, limit, shortBy, orderBy, query
        );
        List<IncomeExpenseResDto> incomeExpenseResList = expensesIncomePage.getContent().stream().map(incomeExpenseMapper::toResponse).toList();

        return BaseResponse.success(incomeExpenseResList, expensesIncomePage, "successful");
    }

}
