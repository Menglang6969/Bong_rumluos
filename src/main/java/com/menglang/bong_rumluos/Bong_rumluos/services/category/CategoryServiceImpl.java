package com.menglang.bong_rumluos.Bong_rumluos.services.category;

import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryMapper;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Category;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.ConflictException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category create(CategoryDTO data) throws Exception {
       Category category= categoryMapper.toEntity(data,categoryRepository);
        log.info("category :{}",category.getName());
      try {
          return categoryRepository.save(category);
      }catch (Exception e){
          throw new BadRequestException(e.getMessage());
      }
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("category not found"));
    }

    @Override
    public Category update(Long id, CategoryDTO data) {
        try {
            if(categoryRepository.existsByNameAndIdNot(data.getName(),id)){
                throw new ConflictException("Category already Exist");
            }
            Category updateCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category Not found"));
            Category parent=validateParent(data);
            updateCategory.setName(data.getName());
            updateCategory.setColor(data.getColor());
            updateCategory.setDescription(data.getDescription());
            updateCategory.setParent(parent);
            return categoryRepository.save(updateCategory);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Category delete(Long id) {
        Category deleteCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        try {
            categoryRepository.delete(deleteCategory);
            return deleteCategory;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    private Category validateParent(CategoryDTO request) {
        Optional<Category> parent = Optional.empty();
        if (request!=null && request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId());
        }
        return parent.orElse(null);

    }
}
