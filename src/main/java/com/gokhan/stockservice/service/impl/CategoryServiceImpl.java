package com.gokhan.stockservice.service.impl;

import com.gokhan.stockservice.model.entity.Category;
import com.gokhan.stockservice.model.request.CreateCategoryRequest;
import com.gokhan.stockservice.model.response.CreateCategoryResponse;
import com.gokhan.stockservice.repository.CategoryRepository;
import com.gokhan.stockservice.service.CategoryService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CreateCategoryResponse save(CreateCategoryRequest request) {
        final String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Category categoryIndb = categoryRepository.save(Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .updateUser(user)
                .build());
        return CreateCategoryResponse.builder()
                .id(categoryIndb.getId())
                .name(categoryIndb.getName())
                .description(categoryIndb.getDescription())
                .updatedDate(categoryIndb.getUpdatedDate())
                .updatedUser(categoryIndb.getUpdateUser())
                .build();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

}
