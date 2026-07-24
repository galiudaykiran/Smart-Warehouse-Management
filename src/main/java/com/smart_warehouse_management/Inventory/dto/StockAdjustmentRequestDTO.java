
package com.smart_warehouse_management.Inventory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockAdjustmentRequestDTO {

    @NotNull(message = "Warehouse Id is required")
    private Long warehouseId;

    @NotNull(message = "Product Id is required")
    private Long productId;

    @NotNull(message = "Adjusted Quantity is required")
    private Integer adjustedQuantity;

    @NotNull(message = "User Id is required")
    private Long userId;

    private String reason;
}