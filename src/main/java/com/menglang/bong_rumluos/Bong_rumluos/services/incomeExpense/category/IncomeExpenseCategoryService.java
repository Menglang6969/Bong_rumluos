package com.menglang.bong_rumluos.Bong_rumluos.services.incomeExpense.category;

import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.category.IncomeExpenseCategoryReqDto;
import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.category.IncomeExpenseCategoryResDto;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IncomeExpenseCategoryService {
    IncomeExpenseCategoryResDto create(IncomeExpenseCategoryReqDto dto) throws BadRequestException;

    IncomeExpenseCategoryResDto update(Long id, IncomeExpenseCategoryReqDto dto) throws BadRequestException;

    IncomeExpenseCategoryResDto view(Long id) throws NotFoundException;

    IncomeExpenseCategoryResDto delete(Long id) throws BadRequestException;

    List<IncomeExpenseCategoryResDto> getAll(int page, int limit, String shortBy, String orderBy, String query);
}
