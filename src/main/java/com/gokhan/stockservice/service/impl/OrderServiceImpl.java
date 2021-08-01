package com.gokhan.stockservice.service.impl;

import com.gokhan.stockservice.auth.UserDetailsImpl;
import com.gokhan.stockservice.exception.BusinessException;
import com.gokhan.stockservice.exception.ExceptionConstants;
import com.gokhan.stockservice.model.entity.Book;
import com.gokhan.stockservice.model.entity.Order;
import com.gokhan.stockservice.model.entity.Stock;
import com.gokhan.stockservice.model.request.CreateOrderRequest;
import com.gokhan.stockservice.model.request.GetAllOrderDateSearch;
import com.gokhan.stockservice.model.response.CreateOrderResponse;
import com.gokhan.stockservice.repository.OrderRepository;
import com.gokhan.stockservice.service.BookService;
import com.gokhan.stockservice.service.OrderService;
import com.gokhan.stockservice.service.StockService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final StockService stockService;

    public OrderServiceImpl(OrderRepository orderRepository, BookService bookService, StockService stockService) {
        this.orderRepository = orderRepository;
        this.bookService = bookService;
        this.stockService = stockService;
    }

    @Transactional
    @Override
    public CreateOrderResponse order(CreateOrderRequest request) {

        UserDetailsImpl user = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final Book book = bookService.findById(request.getBookId())
                .orElseThrow(() -> new BusinessException("Book Not Found!", ExceptionConstants.BOOK_NOT_FOUND));

        Stock stock = stockService.findByBook_Id(book.getId())
                .orElseThrow(() -> new BusinessException("Book Stock Not Found!", ExceptionConstants.STOCK_NOT_FOUND));

        if (request.getQuantity() > stock.getQuantity())
            throw new BusinessException("Book Stock Not Enough!", ExceptionConstants.BOOK_STOCK_NOT_ENOUGH);

        BigDecimal percentage = this.calculatePercentage(request.getDiscount(), book.getPrice());

        BigDecimal totalPrice = (book.getPrice()
                .subtract(percentage))
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        stockService
                .updateStockQuantity(stock.getQuantity() - request.getQuantity(), stock.getId());

        Order order = orderRepository.save(Order.builder()
                .discount(request.getDiscount())
                .price(totalPrice)
                .book(Collections.singletonList(book))
                .quantity(request.getQuantity())
                .updateUser(user.getUsername())
                .userId(user.getId())
                .build());

        return CreateOrderResponse.builder()
                .id(order.getId())
                .books(order.getBook())
                .userId(order.getUserId())
                .totalAmount(order.getPrice())
                .quantity(order.getQuantity())
                .build();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findByUserIdAndActive(Long id, boolean isActive) {
        return orderRepository.findByUserIdAndActive(id, isActive);
    }

    @Override
    public List<Order> getAllBetweenDates(GetAllOrderDateSearch request) {
        return orderRepository.getAllBetweenDates(request.getStartDate(), request.getEndDate());
    }

    public BigDecimal calculatePercentage(BigDecimal obtained, BigDecimal total) {
        return obtained.multiply(BigDecimal.valueOf(100.0)).divide(total);
    }
}
