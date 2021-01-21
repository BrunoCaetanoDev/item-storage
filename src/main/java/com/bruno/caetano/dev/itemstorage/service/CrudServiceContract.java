package com.bruno.caetano.dev.itemstorage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudServiceContract<T extends Object> {

    Page<T> findAll(T entity, Pageable pageRequest);

    T findBydId(String id);

    T save(T item);

    T update(T item);

    void deleteById(String id);

}
