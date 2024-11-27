package com.menglang.bong_rumluos.Bong_rumluos.dto.product;

import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryBaseResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryDTO;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Category;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Product;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
     Logger log = LoggerFactory.getLogger(ProductMapper.class);

    @Mapping(target = "category",source = "category",qualifiedByName = "getCategory")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategory")
    @Mapping(target = "id", ignore = true)  // Ignore the id field
    @Mapping(target = "createdAt", ignore = true)  // Ignore the createdAt field
    @Mapping(target = "createdBy", ignore = true)  // Ignore the createdBy field
    @Mapping(target = "updatedAt", ignore = true)  // Ignore the updatedAt field
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt",ignore = true)
    void updateProductFromDTO(ProductRequest productRequest, @MappingTarget Product product);

    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategory")
    @Mapping(target = "deletedAt",ignore = true)
    Product toEntity(ProductRequest product);

    CategoryBaseResponse toCategoryBase(Category category);

    @Named("mapCategory")
    default Category mapCategory(Long id) {
        Category category = new Category();
        category.setId(id);
        return category;
    }

    @Named("getCategory")
    default CategoryBaseResponse getCategory(Category category){
        log.info("category data: {}",category.getName());
        return this.toCategoryBase(category);
    }

}
