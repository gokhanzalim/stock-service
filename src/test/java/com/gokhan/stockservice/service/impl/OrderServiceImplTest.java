package com.gokhan.stockservice.service.impl;

import com.gokhan.stockservice.auth.UserDetailsImpl;
import com.gokhan.stockservice.model.entity.Book;
import com.gokhan.stockservice.model.entity.Category;
import com.gokhan.stockservice.model.entity.Order;
import com.gokhan.stockservice.model.entity.Stock;
import com.gokhan.stockservice.model.request.CreateOrderRequest;
import com.gokhan.stockservice.model.response.CreateOrderResponse;
import com.gokhan.stockservice.repository.OrderRepository;
import com.gokhan.stockservice.service.BookService;
import com.gokhan.stockservice.service.OrderService;
import com.gokhan.stockservice.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderRepository orderRepository;
    private BookService bookService;
    private StockService stockService;
    private OrderService orderService;
    @MockBean
    private Authentication authentication;
    @MockBean
    private SecurityContext securityContext;

    @BeforeEach
    public void setUp() {
        orderRepository = mock(OrderRepository.class);
        bookService = mock(BookService.class);
        stockService = mock(StockService.class);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        orderService = new OrderServiceImpl(orderRepository, bookService, stockService);
        UserDetailsImpl userDetails =
                new UserDetailsImpl(1L,"gokhan","gzalim01@gmail.com","12345", Collections.EMPTY_LIST);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);

    }

    @Test
    public void order() {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .amount(BigDecimal.TEN).bookId(1L).discount(BigDecimal.ONE).quantity(2).build();

        Book book = Book.builder().name("Gone Girl").id(1L)
                .category(new Category()).price(BigDecimal.TEN).build();

        when(bookService.findById(anyLong())).thenReturn(Optional.of(book));

        Stock stock = Stock.builder().book(book).id(1L).quantity(25).build();

        when(stockService.findByBook_Id(anyLong())).thenReturn(Optional.of(stock));

        doNothing().when(stockService).updateStockQuantity(anyInt(),anyLong());

        Order order = Order.builder().id(1L)
                .book(Collections.singletonList(book)).price(BigDecimal.TEN).quantity(20).build();

        when(orderRepository.save(any())).thenReturn(order);

        CreateOrderResponse response = orderService.order(request);

        assertEquals(response.getBooks().get(0).getId(), request.getBookId());


    }

    @Test
    void findById() {
        Order order = Order.builder().id(1L)
                .book(Collections.EMPTY_LIST).price(BigDecimal.TEN).quantity(20).build();

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Order result = orderService.findById(anyLong()).orElse(null);

        assertEquals(result.getId(), order.getId());
    }

    @Test
    void findByUserIdAndActive() {
        Order order = Order.builder().id(1L)
                .book(Collections.EMPTY_LIST).price(BigDecimal.TEN).quantity(20).build();
        when(orderRepository.findByUserIdAndActive(anyLong(), anyBoolean()))
                .thenReturn(Collections.singletonList(order));

        List<Order> orders = orderService.findByUserIdAndActive(anyLong(), anyBoolean());

        assertEquals(orders.get(0).getId(),order.getId());
    }
}