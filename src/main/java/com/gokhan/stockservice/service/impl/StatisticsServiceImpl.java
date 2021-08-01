package com.gokhan.stockservice.service.impl;

import com.gokhan.stockservice.model.entity.Order;
import com.gokhan.stockservice.model.request.StatisticsRequest;
import com.gokhan.stockservice.model.response.StatisticsResponse;
import com.gokhan.stockservice.repository.OrderRepository;
import com.gokhan.stockservice.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderRepository orderRepository;

    public StatisticsServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getStatisticMonthly(StatisticsRequest request) {
        return orderRepository.getByYearAndMonth(request.getYear(), request.getMonth());
    }

    @Override
    public List<Order> getAllOfCurrentMonth() {
        return orderRepository.getAllOfCurrentMonth();
    }

    @Override
    public List<StatisticsResponse> getAllOrderStatisticsPerMonth() {
        return orderRepository.getAllOrderStatisticsPerMonth();
    }
}
