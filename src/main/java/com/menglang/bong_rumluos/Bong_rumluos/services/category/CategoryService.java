package com.menglang.bong_rumluos.Bong_rumluos.services.category;

import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryDTO;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Category;

import java.util.List;

public interface CategoryService {
    Category create(CategoryDTO data) throws Exception;
    Category getById(Long id);
    Category update(Long id,CategoryDTO data);
    Category delete(Long id);
    List<Category> getAll();

}
