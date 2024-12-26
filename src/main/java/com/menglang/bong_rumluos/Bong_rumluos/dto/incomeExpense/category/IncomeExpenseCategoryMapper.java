package com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense.category;

import com.menglang.bong_rumluos.Bong_rumluos.entities.IncomeExpenseCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IncomeExpenseCategoryMapper {

    IncomeExpenseCategoryResDto toResponse(IncomeExpenseCategory category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void reqUpdate(IncomeExpenseCategoryReqDto dto, @MappingTarget IncomeExpenseCategory category);

    IncomeExpenseCategory toRequest(IncomeExpenseCategoryReqDto dto);
}
