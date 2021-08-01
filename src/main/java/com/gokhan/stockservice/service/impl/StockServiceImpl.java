package com.gokhan.stockservice.service.impl;

import com.gokhan.stockservice.exception.BusinessException;
import com.gokhan.stockservice.exception.ExceptionConstants;
import com.gokhan.stockservice.model.entity.Book;
import com.gokhan.stockservice.model.entity.Stock;
import com.gokhan.stockservice.model.request.CreateStockRequest;
import com.gokhan.stockservice.model.request.UpdateStockRequest;
import com.gokhan.stockservice.model.response.CreateStockResponse;
import com.gokhan.stockservice.model.response.UpdateStockResponse;
import com.gokhan.stockservice.repository.StockRepository;
import com.gokhan.stockservice.service.BookService;
import com.gokhan.stockservice.service.StockService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final BookService bookService;

    public StockServiceImpl(StockRepository stockRepository, BookService bookService) {
        this.stockRepository = stockRepository;
        this.bookService = bookService;
    }

    @Transactional
    @Override
    public CreateStockResponse save(CreateStockRequest request) {

        Book book = bookService.findById(request.getBookId())
                     .orElseThrow(() -> new BusinessException("Book Not found!", ExceptionConstants.BOOK_NOT_FOUND));

        final String user = SecurityContextHolder.getContext().getAuthentication().getName();
        final Stock bookInDb = stockRepository.save(
                Stock.builder()
                        .name(request.getName())
                        .book(book).updateUser(user)
                        .quantity(request.getQuantity())
                        .build());

        return CreateStockResponse.builder()
                .book(bookInDb.getBook())
                .id(book.getId())
                .name(bookInDb.getName())
                .quantity(bookInDb.getQuantity())
                .updatedUser(bookInDb.getUpdateUser())
                .build();
    }

    @Override
    public Optional<Stock> findByBook_Id(Long bookId) {
        return stockRepository.findByBook_Id(bookId);
    }

    @Override
    public void updateStockQuantity(Integer quantity, Long id) {
        stockRepository.updateStockQuantity(quantity,id);
    }

    @Transactional
    @Override
    public UpdateStockResponse update(UpdateStockRequest request) {
        final Stock stock = stockRepository.findByBook_Id(request.getBookId())
                .orElseThrow(() -> new BusinessException("Book Not Found", ExceptionConstants.BOOK_NOT_FOUND));
        stock.setQuantity(request.getQuantity());
        stockRepository.save(stock);
        return UpdateStockResponse.builder()
                .book(stock.getBook())
                .id(stock.getId())
                .quantity(stock.getQuantity())
                .name(stock.getName())
                .build();
    }
}
