package com.menglang.bong_rumluos.Bong_rumluos.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableResponse {
    public static Pageable handlePageable(int page, int size, String orderBy, String sortBy) {
        Sort sort = Sort.by(sortBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, orderBy);
        return PageRequest.of(page - 1, size, sort);

    }
}
