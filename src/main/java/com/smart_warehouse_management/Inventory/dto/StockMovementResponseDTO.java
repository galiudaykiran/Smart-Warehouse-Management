package com.smart_warehouse_management.Inventory.dto;

import java.time.LocalDateTime;

import com.smart_warehouse_management.Inventory.enums.MovementType;

import lombok.Data;

@Data
public class StockMovementResponseDTO {

    private Long movementId;

    private Long productId;

    private String productName;

    private Long warehouseFrom;

    private Long warehouseTo;

    private Integer quantity;

    private MovementType movementType;

    private Long userId;

    private LocalDateTime createdAt;
}