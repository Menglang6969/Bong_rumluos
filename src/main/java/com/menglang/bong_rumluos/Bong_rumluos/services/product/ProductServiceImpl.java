package com.menglang.bong_rumluos.Bong_rumluos.services.product;

import com.menglang.bong_rumluos.Bong_rumluos.dto.product.ProductMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.product.ProductRequest;
import com.menglang.bong_rumluos.Bong_rumluos.dto.product.ProductResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Category;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Product;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.CategoryRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    public ProductResponse create(ProductRequest productRequest) throws BadRequestException {
        try {
           Category category= categoryRepository.findById(productRequest.categoryId()).orElseThrow(() -> new NotFoundException("Category Not Found."));
            Product product = productMapper.toEntity(productRequest);
            Product savedProduct=productRepository.save(product);
            savedProduct.setCategory(category);
            return productMapper.toProductResponse(savedProduct);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    @Override
    public ProductResponse getById(Long id) throws BadRequestException {
        Product product = findProductById(id);
        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest productRequest) throws BadRequestException {
        try {
            Product existProduct = findProductById(id);
            Category category= categoryRepository.findById(productRequest.categoryId()).orElseThrow(() -> new NotFoundException("Category Not Found."));
            productMapper.updateProductFromDTO(productRequest, existProduct);
            log.info("exist product update name :{}",existProduct.getName());
            Product productUpdated=productRepository.save(existProduct);
            productUpdated.setCategory(category);
            return productMapper.toProductResponse(productUpdated);

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ProductResponse delete(Long id) throws BadRequestException {
        Product product = findProductById(id);
        try {
            productRepository.delete(product);
            return productMapper.toProductResponse(product);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public List<ProductResponse> getAll(int page, int limit, String orderBy, String sortBy, boolean isTrash, String query,Long category_id) throws BadRequestException {
        Sort soring = orderBy.equals("ASC") ? Sort.by(Sort.Direction.ASC, sortBy) : Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page - 1, limit, soring);
        Page<Product> allProducts = productRepository.findAllByNameOrCodeAndDeletedIsNotNull(query,category_id, pageable);
        return allProducts.getContent().stream().map(this.productMapper::toProductResponse).toList();
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product Not Found"));
    }
}
