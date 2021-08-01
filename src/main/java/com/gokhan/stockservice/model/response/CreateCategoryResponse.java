package com.gokhan.stockservice.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public class CreateCategoryResponse{

    private Long id;
    private String name;
    private String description;
    private LocalDateTime updatedDate;
    private String updatedUser;
}
