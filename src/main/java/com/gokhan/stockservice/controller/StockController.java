package com.gokhan.stockservice.controller;

import com.gokhan.stockservice.model.request.CreateStockRequest;
import com.gokhan.stockservice.model.request.UpdateStockRequest;
import com.gokhan.stockservice.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> save(@Valid @RequestBody CreateStockRequest createStockRequest) {
        return ResponseEntity.ok(stockService.save(createStockRequest));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateStockRequest request) {
        return ResponseEntity.ok(stockService.update(request));
    }
}
