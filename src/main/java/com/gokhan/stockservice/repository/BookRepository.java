package com.gokhan.stockservice.repository;

import com.gokhan.stockservice.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
