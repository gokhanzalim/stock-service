package com.gokhan.stockservice.model.response;

import java.math.BigDecimal;

public interface StatisticsResponse {
    Integer getYear();

    Integer getMonth();

    Integer getOrderCount();

    Integer getBookCount();

    BigDecimal getTotalAmount();
}
