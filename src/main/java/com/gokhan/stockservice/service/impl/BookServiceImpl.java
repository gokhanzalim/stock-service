package com.gokhan.stockservice.service.impl;

import com.gokhan.stockservice.exception.BusinessException;
import com.gokhan.stockservice.exception.ExceptionConstants;
import com.gokhan.stockservice.model.entity.Book;
import com.gokhan.stockservice.model.entity.Category;
import com.gokhan.stockservice.model.request.CreateBookRequest;
import com.gokhan.stockservice.model.response.CreateBookResponse;
import com.gokhan.stockservice.repository.BookRepository;
import com.gokhan.stockservice.service.BookService;
import com.gokhan.stockservice.service.CategoryService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
    }

    @Override
    public CreateBookResponse save(CreateBookRequest request) {
        final Category category = categoryService.findById(request.getCategoryId())
                .orElseThrow(() -> new BusinessException("Category not found!", ExceptionConstants.CATEGORY_NOT_FOUND));
        final String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Book savedBook = bookRepository.save(Book.builder()
                .category(category)
                .name(request.getName())
                .price(request.getPrice())
                .updateUser(user)
                .build());

        return CreateBookResponse.builder()
                .id(savedBook.getId())
                .name(savedBook.getName())
                .price(savedBook.getPrice())
                .updatedDate(savedBook.getUpdatedDate())
                .updatedUser(savedBook.getUpdateUser())
                .category(savedBook.getCategory().getId())
                .build();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }
}
