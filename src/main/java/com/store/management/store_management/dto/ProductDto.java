package com.store.management.store_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    @NotNull(message = "Product name cannot be null!")
    @NotBlank(message = "Product name cannot be empty!")
    private String productName;
    @Min(value = 1, message = "Product price cannot be lowe than 1!")
    private double productPrice;
}
