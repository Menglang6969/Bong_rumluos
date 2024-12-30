package com.menglang.bong_rumluos.Bong_rumluos.services.incomeExpense.category;

import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.category.IncomeExpenseCategoryMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.category.IncomeExpenseCategoryReqDto;
import com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.category.IncomeExpenseCategoryResDto;
import com.menglang.bong_rumluos.Bong_rumluos.entities.IncomeExpenseCategory;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.IncomeExpenseCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncomeExpenseCategoryServiceImpl implements IncomeExpenseCategoryService {
    private final IncomeExpenseCategoryRepository repository;
    private final IncomeExpenseCategoryMapper mapper;

    @Override
    public IncomeExpenseCategoryResDto create(IncomeExpenseCategoryReqDto dto) throws BadRequestException {
        IncomeExpenseCategory reqData = mapper.toRequest(dto);
        try {
            return mapper.toResponse(repository.save(reqData));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public IncomeExpenseCategoryResDto update(Long id, IncomeExpenseCategoryReqDto dto) throws BadRequestException {
        IncomeExpenseCategory updateData = this.findById(id);
        try {
            mapper.reqUpdate(dto, updateData);
            return mapper.toResponse(repository.save(updateData));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public IncomeExpenseCategoryResDto view(Long id) throws NotFoundException {
        return mapper.toResponse(this.findById(id));
    }

    @Override
    public IncomeExpenseCategoryResDto delete(Long id) throws BadRequestException {
        IncomeExpenseCategory incomeExpenseCategory = findById(id);
        try {
            repository.delete(incomeExpenseCategory);
            return mapper.toResponse(incomeExpenseCategory);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Page<IncomeExpenseCategory> getAll(int page, int limit, String shortBy, String orderBy, String query) {
        Sort sort = Sort.by(orderBy.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, shortBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        return repository.findAllByTitle(query, pageable);

    }

    private IncomeExpenseCategory findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Data Not Found."));
    }
}
