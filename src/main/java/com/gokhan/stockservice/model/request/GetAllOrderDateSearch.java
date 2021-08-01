package com.gokhan.stockservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllOrderDateSearch {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
