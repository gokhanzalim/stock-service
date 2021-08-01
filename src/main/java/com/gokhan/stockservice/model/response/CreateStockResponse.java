package com.gokhan.stockservice.model.response;

import com.gokhan.stockservice.model.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CreateStockResponse {

    private Long id;
    private String name;
    private Book book;
    private int quantity;
    private String updatedUser;
}
