package com.gokhan.stockservice.service;

import com.gokhan.stockservice.model.entity.Order;
import com.gokhan.stockservice.model.request.StatisticsRequest;
import com.gokhan.stockservice.model.response.StatisticsResponse;

import java.util.List;

public interface StatisticsService {
    List<Order> getStatisticMonthly(StatisticsRequest request);

    List<Order> getAllOfCurrentMonth();

    List<StatisticsResponse> getAllOrderStatisticsPerMonth();
}
