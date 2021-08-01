package com.gokhan.stockservice.service.impl;

import com.gokhan.stockservice.model.entity.Order;
import com.gokhan.stockservice.model.request.StatisticsRequest;
import com.gokhan.stockservice.model.response.StatisticsResponse;
import com.gokhan.stockservice.repository.CategoryRepository;
import com.gokhan.stockservice.repository.OrderRepository;
import com.gokhan.stockservice.service.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StatisticsServiceImplTest {

    private OrderRepository orderRepository;
    private StatisticsService statisticsService;

    @BeforeEach
    public void setUp() {
        orderRepository = mock(OrderRepository.class);
        statisticsService = new StatisticsServiceImpl(orderRepository);
    }

    @Test
    void testStatisticMonthly() {
        StatisticsRequest request = new StatisticsRequest(2021,5);
        Order order = Order.builder().id(1L).build();
        when(orderRepository.getByYearAndMonth(anyInt(),anyInt())).thenReturn(Collections.singletonList(order));
        final List<Order> result = statisticsService.getStatisticMonthly(request);
        assertEquals(result.get(0).getId(),order.getId());

    }

    @Test
    void testAllOfCurrentMonth() {
        Order order = Order.builder().id(1L).build();
        when(orderRepository.getAllOfCurrentMonth()).thenReturn(Collections.singletonList(order));
        final List<Order> result = statisticsService.getAllOfCurrentMonth();
        assertEquals(result.get(0).getId(),order.getId());
    }

    @Test
    void testAllOrderStatisticsPerMonth() {
        when(orderRepository.getAllOrderStatisticsPerMonth()).thenReturn(Collections.EMPTY_LIST);
        List<StatisticsResponse> result = statisticsService.getAllOrderStatisticsPerMonth();
        assertEquals(result.size(),0);
    }
}