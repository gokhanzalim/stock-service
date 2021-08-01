package com.gokhan.stockservice.model.response;

import com.gokhan.stockservice.model.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CreateOrderResponse {
    private Long id;
    private BigDecimal totalAmount;
    private Long userId;
    private Integer quantity;
    private List<Book> books;
}
