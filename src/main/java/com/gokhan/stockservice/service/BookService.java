package com.gokhan.stockservice.service;

import com.gokhan.stockservice.model.entity.Book;
import com.gokhan.stockservice.model.request.CreateBookRequest;
import com.gokhan.stockservice.model.response.CreateBookResponse;

import java.util.Optional;

public interface BookService {
    CreateBookResponse save(CreateBookRequest request);

    Optional<Book> findById(Long id);
}
