
package com.smart_warehouse_management.Inventory.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InventoryResponseDTO {

    private Long inventoryId;

    private Long warehouseId;

    private Long productId;

    private String productName;

    private Integer availableQuantity;

    private Integer reservedQuantity;

    private Integer damagedQuantity;

    private LocalDateTime updatedAt;
}