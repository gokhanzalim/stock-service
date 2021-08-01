package com.gokhan.stockservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CreateBookResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long category;
    private LocalDateTime updatedDate;
    private String updatedUser;
}
