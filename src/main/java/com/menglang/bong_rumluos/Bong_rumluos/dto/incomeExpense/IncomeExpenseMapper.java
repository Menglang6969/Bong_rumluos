package com.menglang.bong_rumluos.Bong_rumluos.dto.incomeExpense;

import com.menglang.bong_rumluos.Bong_rumluos.entities.IncomeExpense;
import com.menglang.bong_rumluos.Bong_rumluos.entities.IncomeExpenseCategory;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.IncomeExpenseCategoryRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IncomeExpenseMapper {


    IncomeExpenseResDto toResponse(IncomeExpense incomeExpense);

    @Mapping(target = "category",source = "category",qualifiedByName = "mapCategoryIncomeExpense")
    IncomeExpense toRequest(IncomeExpenseReqDto reqDto, @Context IncomeExpenseCategoryRepository repository);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "category",source = "category",qualifiedByName = "mapCategoryIncomeExpense")
    void reqUpdate(IncomeExpenseReqDto dto,@Context IncomeExpenseCategoryRepository repository, @MappingTarget IncomeExpense expenseIncome);

    @Named("mapCategoryIncomeExpense")
    default IncomeExpenseCategory mapCategoryIncomeExpense(Long id,@Context IncomeExpenseCategoryRepository repository){
        return repository.findById(id).orElseThrow(()->new NotFoundException("Category Not Found."));
    }
}
