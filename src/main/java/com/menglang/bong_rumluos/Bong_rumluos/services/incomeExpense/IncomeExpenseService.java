package com.menglang.bong_rumluos.Bong_rumluos.services.incomeExpense;

import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.IncomeExpenseReqDto;
import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.IncomeExpenseResDto;
import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.category.IncomeExpenseCategoryReqDto;
import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.category.IncomeExpenseCategoryResDto;
import com.menglang.bong_rumluos.Bong_rumluos.entities.IncomeExpense;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IncomeExpenseService {
    IncomeExpenseResDto create(IncomeExpenseReqDto dto) throws BadRequestException;

    IncomeExpenseResDto update(Long id, IncomeExpenseReqDto dto) throws BadRequestException;

    IncomeExpenseResDto view(Long id) throws NotFoundException;

    IncomeExpenseResDto delete(Long id) throws BadRequestException;

    Page<IncomeExpense> getAll(int page, int limit, String shortBy, String orderBy, String query);
}
