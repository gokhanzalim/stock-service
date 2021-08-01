package com.gokhan.stockservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CreateStockRequest {
    @NotNull
    private String name;
    @NotNull
    private Long bookId;
    @NotNull
    private int quantity;
}
