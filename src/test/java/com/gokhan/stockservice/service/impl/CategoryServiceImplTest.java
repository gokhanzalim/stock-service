package com.gokhan.stockservice.service.impl;

import com.gokhan.stockservice.model.entity.Book;
import com.gokhan.stockservice.model.entity.Category;
import com.gokhan.stockservice.model.request.CreateCategoryRequest;
import com.gokhan.stockservice.model.response.CreateCategoryResponse;
import com.gokhan.stockservice.repository.CategoryRepository;
import com.gokhan.stockservice.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    private CategoryService categoryService;

    private CategoryRepository categoryRepository;
    @MockBean
    private Authentication authentication;
    @MockBean
    private SecurityContext securityContext;

    @BeforeEach
    public void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void saveCategory() {
        CreateCategoryRequest request = CreateCategoryRequest.builder().name("Mystery").description("Mystery Books").build();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("gokhan");
        when(categoryRepository.save(any())).thenReturn(
                Category.builder()
                        .books(Collections.emptyList())
                        .name("Mystery")
                        .build());

        CreateCategoryResponse response = categoryService.save(request);
        assertEquals(response.getName(), request.getName());

    }

    @Test
    public void testFindById_whenBookIdExists_shouldReturnBook() {
        Category category = Category.builder().id(1L).name("Mystery").build();

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        Category result = categoryService.findById(anyLong()).orElse(null);

        assertEquals(result.getId(), category.getId());
    }

    @Test
    public void testFindById_whenBookIdDoesNotExists_shouldReturnEmpty() {

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Category> result = categoryService.findById(anyLong());

        assertEquals(result, Optional.empty());
    }
}