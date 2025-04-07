package com.menglang.bong_rumluos.Bong_rumluos.services.category;

import com.menglang.bong_rumluos.Bong_rumluos.annotations.CanView;
import com.menglang.bong_rumluos.Bong_rumluos.annotations.IsAdmin;
import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryMapper;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Category;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.*;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CategoryRepository;
import com.menglang.bong_rumluos.Bong_rumluos.utils.PageableResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @PreAuthorize("@userAuth.canCreateWithPermission(authentication,'CATEGORY')")
    @Override
    public Category create(CategoryDTO data) throws Exception {
        boolean existCategory = categoryRepository.existsByName(data.getName());
        if (existCategory) throw new DuplicateRequestException("Category Already Exist!");
        Category category = categoryMapper.toEntity(data, categoryRepository);
        try {
            return categoryRepository.save(category);
        } catch (HttpClientErrorException.Forbidden e) {
            throw new ForbiddenException(e.getMessage());
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @PreAuthorize("@userAuth.canViewWithPermission(authentication,'CATEGORY')")
    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("category not found"));
    }

    @PreAuthorize("@userAuth.canEditWithPermission(authentication,'CATEGORY')")
    @Override
    public Category update(Long id, CategoryDTO data) {
        try {
            if (categoryRepository.existsByNameAndIdNot(data.getName(), id)) {
                throw new ConflictException("Category already Exist");
            }
            Category updateCategory = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category Not found"));
            Category parent = validateParent(data);
            updateCategory.setName(data.getName());
            updateCategory.setColor(data.getColor());
            updateCategory.setDescription(data.getDescription());
            updateCategory.setParent(parent);
            return categoryRepository.save(updateCategory);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @PreAuthorize("@userAuth.canDeleteWithPermission(authentication,'CATEGORY')")
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

    @PreAuthorize("@userAuth.canViewWithPermission(authentication,'CATEGORY')")
    @Override
    public Page<Category> getAll(int page, int size, String orderBy, String sortBy, String query) {
        Pageable pageable = PageableResponse.handlePageable(page, size, orderBy, sortBy);
        return categoryRepository.findAllByName(query, pageable);
    }

    private Category validateParent(CategoryDTO request) {
        Optional<Category> parent = Optional.empty();
        if (request != null && request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId());
        }
        return parent.orElse(null);

    }
}
