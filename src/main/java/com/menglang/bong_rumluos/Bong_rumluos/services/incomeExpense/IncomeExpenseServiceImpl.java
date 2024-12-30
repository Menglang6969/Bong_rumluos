package com.menglang.bong_rumluos.Bong_rumluos.services.incomeExpense;

import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.IncomeExpenseMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.IncomeExpenseReqDto;
import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.IncomeExpenseResDto;
import com.menglang.bong_rumluos.Bong_rumluos.entities.IncomeExpense;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.IncomeExpenseCategoryRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.IncomeExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeExpenseServiceImpl implements IncomeExpenseService {
    private final IncomeExpenseRepository repository;
    private final IncomeExpenseCategoryRepository incomeExpenseCategoryRepository;
    private final IncomeExpenseMapper mapper;

    @Override
    public IncomeExpenseResDto create(IncomeExpenseReqDto dto) throws BadRequestException {
        IncomeExpense incomeExpense = mapper.toRequest(dto, incomeExpenseCategoryRepository);
        try {
            return mapper.toResponse(repository.save(incomeExpense));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public IncomeExpenseResDto update(Long id, IncomeExpenseReqDto dto) throws BadRequestException {
        IncomeExpense updateIncExp = findById(id);
        mapper.reqUpdate(dto, incomeExpenseCategoryRepository, updateIncExp);
        try {
            return mapper.toResponse(repository.save(updateIncExp));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public IncomeExpenseResDto view(Long id) throws NotFoundException {
        return mapper.toResponse(findById(id));
    }

    @Override
    public IncomeExpenseResDto delete(Long id) throws BadRequestException {
        IncomeExpense incomeExpense = findById(id);
        try {
            repository.delete(incomeExpense);
            return mapper.toResponse(incomeExpense);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Page<IncomeExpense> getAll(int page, int limit, String shortBy, String orderBy, String query) {
        Sort sort = Sort.by(orderBy.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, shortBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        return repository.findAllDescription(query, pageable);
    }

    private IncomeExpense findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Data Not Found."));
    }
}
