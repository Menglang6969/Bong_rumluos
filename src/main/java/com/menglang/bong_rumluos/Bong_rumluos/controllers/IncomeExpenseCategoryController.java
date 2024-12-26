package com.menglang.bong_rumluos.Bong_rumluos.controllers;

import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.category.IncomeExpenseCategoryReqDto;
import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.category.IncomeExpenseCategoryResDto;
import com.menglang.bong_rumluos.Bong_rumluos.services.incomeExpense.category.IncomeExpenseCategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income_expense_category")
@RequiredArgsConstructor
public class IncomeExpenseCategoryController {
    private static final Logger log = LoggerFactory.getLogger(IncomeExpenseCategoryController.class);
    private final IncomeExpenseCategoryService incomeExpenseCategoryService;


    @PostMapping
    public ResponseEntity<IncomeExpenseCategoryResDto> create(@RequestBody IncomeExpenseCategoryReqDto dto) {
        log.info("data request: {}", dto.title());

        return ResponseEntity.ok(incomeExpenseCategoryService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeExpenseCategoryResDto> update(@PathVariable("id") Long id, @RequestBody IncomeExpenseCategoryReqDto dto) {
        log.info("data request: {}", dto.title());
        return ResponseEntity.ok(incomeExpenseCategoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IncomeExpenseCategoryResDto> delete(@PathVariable("id") Long id) {
        log.info("data request: {}", id);
        return ResponseEntity.ok(incomeExpenseCategoryService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeExpenseCategoryResDto> getById(@PathVariable("id") Long id) {
        log.info("data request: {}", id);
        return ResponseEntity.ok(incomeExpenseCategoryService.view(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<IncomeExpenseCategoryResDto>> getById(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "shortBy", defaultValue = "description") String shortBy,
            @RequestParam(name = "orderBy", defaultValue = "DESC") String orderBy,
            @RequestParam(name = "query", defaultValue = "") String query
    ) {
        log.info("data request: ");
        return ResponseEntity.ok(incomeExpenseCategoryService.getAll(
                page, limit, shortBy, orderBy, query
        ));
    }
}
