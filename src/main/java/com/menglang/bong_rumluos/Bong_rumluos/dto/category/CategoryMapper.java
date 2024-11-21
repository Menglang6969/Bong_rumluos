package com.menglang.bong_rumluos.Bong_rumluos.dto.category;

import com.menglang.bong_rumluos.Bong_rumluos.entities.Category;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CategoryRepository;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "parent",source = "parent",qualifiedByName = "mapParent")
    CategoryResponse toResponse(Category category);

    @Mapping(target = "parent",source = "parentId",qualifiedByName = "mapPrentById")
    Category toEntity(CategoryDTO categoryDTO,@Context CategoryRepository categoryRepository);

    @Named("mapParent")
    default CategoryResponse mapParent(Category category){
        return toResponse(category);
    }

    @Named("mapPrentById")
    default Category mapPrentById(Long parentId,@Context CategoryRepository categoryRepository) throws Exception {
        if (parentId!= null) {
            return categoryRepository.findById(parentId).orElseThrow(()->new Exception("Category Not Found"));
        }
        return null;
    }

}
