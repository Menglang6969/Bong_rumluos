package com.menglang.bong_rumluos.Bong_rumluos.controllers;

import com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse.BaseResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.product.ProductMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.product.ProductRequest;
import com.menglang.bong_rumluos.Bong_rumluos.dto.product.ProductResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Product;
import com.menglang.bong_rumluos.Bong_rumluos.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productDTO) {
        return ResponseEntity.ok(productService.create(productDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest productDTO) {
        return ResponseEntity.ok(productService.update(id, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse> getAllProducts(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "orderBy", defaultValue = "ASC") String orderBy,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "isTrash", defaultValue = "isTrash") boolean isTrash,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "category", defaultValue = "") Long category_id
    ) {
        Page<Product> products = productService.getAll(page, limit, orderBy, sortBy, isTrash, query, category_id);
        List<ProductResponse> productResponses = products.getContent().stream().map(this.productMapper::toProductResponse).toList();

        return BaseResponse.success(productResponses,products,"successful");
    }
}
