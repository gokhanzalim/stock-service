package com.gokhan.stockservice.service;

import com.gokhan.stockservice.model.entity.Category;
import com.gokhan.stockservice.model.request.CreateCategoryRequest;
import com.gokhan.stockservice.model.response.CreateCategoryResponse;

import java.util.Optional;

public interface CategoryService {
    CreateCategoryResponse save(CreateCategoryRequest request);

    Optional<Category> findById(Long id);
}
