package com.menglang.bong_rumluos.Bong_rumluos.services.product;

import com.menglang.bong_rumluos.Bong_rumluos.dto.product.ProductRequest;
import com.menglang.bong_rumluos.Bong_rumluos.dto.product.ProductResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Product;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.BaseException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest productRequest) throws BadRequestException;
    ProductResponse getById(Long id) throws BadRequestException;
    ProductResponse update(Long id,ProductRequest productRequest) throws BadRequestException;
    ProductResponse delete(Long id) throws BadRequestException;
    Page<Product> getAll(int page, int pageSize, String orderBy, String sortBy, boolean isTrash, String query, Long category_id) throws BadRequestException;

    BigDecimal getProductPrice(List<Long> product_ids) throws BaseException;
}
