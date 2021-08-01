package com.gokhan.stockservice.model.response;

import com.gokhan.stockservice.model.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStockResponse {
    private Long id;
    private String name;
    private Integer quantity;
    private Book book;
}
