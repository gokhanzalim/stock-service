package com.gokhan.stockservice.service.impl;

import com.gokhan.stockservice.model.entity.Book;
import com.gokhan.stockservice.model.entity.Order;
import com.gokhan.stockservice.model.entity.Stock;
import com.gokhan.stockservice.model.request.CreateStockRequest;
import com.gokhan.stockservice.model.request.UpdateStockRequest;
import com.gokhan.stockservice.model.response.CreateStockResponse;
import com.gokhan.stockservice.model.response.UpdateStockResponse;
import com.gokhan.stockservice.repository.StockRepository;
import com.gokhan.stockservice.service.BookService;
import com.gokhan.stockservice.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class StockServiceImplTest {

    private StockRepository stockRepository;
    private BookService bookService;
    private StockService stockService;
    @MockBean
    private Authentication authentication;
    @MockBean
    private SecurityContext securityContext;

    @BeforeEach
    public void setUp() {
        stockRepository = mock(StockRepository.class);
        bookService = mock(BookService.class);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        stockService = new StockServiceImpl(stockRepository, bookService);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("gokhan");

    }

    @Test
    public void testSave() {
        CreateStockRequest request = CreateStockRequest.builder()
                .quantity(25).bookId(1L).name("Gone Girl").build();
        Book book = Book.builder().price(BigDecimal.TEN).id(1L).name("Gone Girl").build();
        when(bookService.findById(anyLong())).thenReturn(Optional.of(book));
        Stock stock = Stock.builder().id(1L).quantity(25).book(book).build();
        when(stockRepository.save(any())).thenReturn(stock);
        CreateStockResponse response = stockService.save(request);
        assertEquals(response.getBook().getId(),request.getBookId());
    }

    @Test
    public void testFindByBook_Id() {
        Stock stock = Stock.builder().id(1L).quantity(25).book(new Book()).build();

        when(stockRepository.findByBook_Id(anyLong())).thenReturn(Optional.of(stock));

        Stock result = stockService.findByBook_Id(anyLong()).orElse(null);

        assertEquals(result.getId(), stock.getId());
    }

    @Test
    void testUpdateStockQuantity() {
        doNothing().when(stockRepository).updateStockQuantity(anyInt(),anyLong());
        stockService.updateStockQuantity(anyInt(),anyLong());
    }

    @Test
    void testUpdate() {
        UpdateStockRequest  request = new UpdateStockRequest(1L,5);
        Stock stock = Stock.builder().id(1L).quantity(25).book(new Book()).build();
        when(stockRepository.findByBook_Id(anyLong())).thenReturn(Optional.of(stock));
        UpdateStockResponse response = stockService.update(request);
        assertEquals(response.getId(),stock.getId());
    }
}