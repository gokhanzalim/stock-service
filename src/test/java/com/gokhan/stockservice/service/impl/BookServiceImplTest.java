package com.gokhan.stockservice.service.impl;

import com.gokhan.stockservice.exception.BusinessException;
import com.gokhan.stockservice.model.entity.Book;
import com.gokhan.stockservice.model.entity.Category;
import com.gokhan.stockservice.model.request.CreateBookRequest;
import com.gokhan.stockservice.model.response.CreateBookResponse;
import com.gokhan.stockservice.repository.BookRepository;
import com.gokhan.stockservice.service.BookService;
import com.gokhan.stockservice.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookServiceImplTest {

    private BookService bookService;
    private BookRepository bookRepository;
    private CategoryService categoryService;
    @MockBean
    private Authentication authentication;
    @MockBean
    private SecurityContext securityContext;

    @BeforeEach
    public void setUp() {
        bookRepository = mock(BookRepository.class);
        categoryService = mock(CategoryService.class);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        bookService = new BookServiceImpl(bookRepository, categoryService);

    }

    @Test
    public void testSaveBook_whenCategoryExists_ShouldReturnSucces() {
        CreateBookRequest request = new CreateBookRequest("Gone Girl", BigDecimal.TEN, 1L, LocalDateTime.now(), "gokhan");
        Book book = Book.builder().name("Gone Girl").id(1L).category(new Category()).build();

        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(categoryService.findById(anyLong())).thenReturn(Optional.of(new Category()));
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("gokhan");

        when(bookRepository.save(any())).thenReturn(book);

        CreateBookResponse result = bookService.save(request);
        assertEquals(result.getName(), request.getName());


    }

    @Test
    public void testSaveBook_whenCategoryDoesNotExists_ShouldReturnBusinessException() {
        CreateBookRequest request = CreateBookRequest.builder().name("Gone Girl").build();
        assertThrows(BusinessException.class, () -> bookService.save(request));

    }

    @Test
    public void testFindById_whenBookIdExists_shouldReturnBook() {
        Book book = Book.builder().id(1L).name("Gone Girl").build();

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        Book result = bookService.findById(anyLong()).orElse(null);

        assertEquals(result.getId(), book.getId());
    }

    @Test
    public void testFindById_whenBookIdDoesNotExists_shouldReturnEmpty() {

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Book> result = bookService.findById(anyLong());

        assertEquals(result, Optional.empty());
    }
}