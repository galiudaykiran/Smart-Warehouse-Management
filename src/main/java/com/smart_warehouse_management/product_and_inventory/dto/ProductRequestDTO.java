
package com.smart_warehouse_management.product_and_inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductRequestDTO {

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotNull(message = "Category Id is required")
    private Long categoryId;

    @NotNull(message = "Supplier Id is required")
    private Long supplierId;

    @Positive(message = "Price must be greater than zero")
    private Double price;

    @Positive(message = "Cost price must be greater than zero")
    private Double costPrice;

    @Positive(message = "Minimum stock must be greater than zero")
    private Integer minimumStock;
    
    private Integer currentStock;
    private Integer stockStatus;
}