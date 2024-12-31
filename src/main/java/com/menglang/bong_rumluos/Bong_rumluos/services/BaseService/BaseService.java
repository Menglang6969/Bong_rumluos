package com.menglang.bong_rumluos.Bong_rumluos.services.BaseService;

import org.springframework.data.domain.Page;

import java.util.List;

public interface BaseService<T,R,E> {
    T create(R dto);
    T view(Long id);
    T delete(Long id);
    T update(Long id,R dto);
    Page<E> getAll(int page, int size, String orderBy, String sortBy, String query);
}
