package com.menglang.bong_rumluos.Bong_rumluos.controllers;

import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.category.CategoryResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse.BaseResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Category;
import com.menglang.bong_rumluos.Bong_rumluos.services.category.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private final CategoryService categoryService;
    @Autowired
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryDTO dto) throws Exception {
        log.info("invoke category .........");
        return ResponseEntity.ok(categoryMapper.toResponse(categoryService.create(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable("id") Long id, @RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryMapper.toResponse(categoryService.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryMapper.toResponse(categoryService.delete(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryMapper.toResponse(categoryService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(name = "sortBy", defaultValue = "desc") String sortBy,
            @RequestParam(name = "query") String query

    ) {
        Page<Category> categoryPage = categoryService.getAll(
                page, size, orderBy, sortBy, query
        );
//        log.info(" data response: id {}",categoryPage.getContent().get(0).getId());
        List<CategoryResponse> categoryResponses = categoryPage.getContent().stream().map(categoryMapper::toResponse).toList();
        return BaseResponse.success(categoryResponses, categoryPage, "successful");
    }
}
