package com.gokhan.stockservice.service;

import com.gokhan.stockservice.model.entity.Stock;
import com.gokhan.stockservice.model.request.CreateStockRequest;
import com.gokhan.stockservice.model.request.UpdateStockRequest;
import com.gokhan.stockservice.model.response.CreateStockResponse;
import com.gokhan.stockservice.model.response.UpdateStockResponse;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockService {
    CreateStockResponse save(CreateStockRequest request);
    Optional<Stock> findByBook_Id(Long bookId);
    void updateStockQuantity(Integer quantity, Long id);
    UpdateStockResponse update(UpdateStockRequest request);
}
