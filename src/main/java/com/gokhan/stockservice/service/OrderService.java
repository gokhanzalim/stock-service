package com.gokhan.stockservice.service;

import com.gokhan.stockservice.model.entity.Order;
import com.gokhan.stockservice.model.request.CreateOrderRequest;
import com.gokhan.stockservice.model.response.CreateOrderResponse;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    CreateOrderResponse order(CreateOrderRequest request);
    Optional<Order> findById(Long id);
    List<Order> findByUserIdAndActive(Long id, boolean isActive);
}
