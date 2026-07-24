
package com.smart_warehouse_management.Inventory.mapper;

import com.smart_warehouse_management.Inventory.dto.InventoryResponseDTO;
import com.smart_warehouse_management.Inventory.dto.StockMovementResponseDTO;
import com.smart_warehouse_management.Inventory.entity.Inventory;
import com.smart_warehouse_management.Inventory.entity.StockMovement;

public class InventoryMapper {

    // Inventory Entity -> Response DTO
    public static InventoryResponseDTO toInventoryResponseDTO(Inventory inventory) {

        if (inventory == null) {
            return null;
        }

        InventoryResponseDTO dto = new InventoryResponseDTO();

        dto.setInventoryId(inventory.getInventoryId());
        dto.setWarehouseId(inventory.getWarehouseId());

        dto.setProductId(inventory.getProduct().getProductId());
        dto.setProductName(inventory.getProduct().getProductName());

        dto.setAvailableQuantity(inventory.getAvailableQuantity());
        dto.setReservedQuantity(inventory.getReservedQuantity());
        dto.setDamagedQuantity(inventory.getDamagedQuantity());

        dto.setUpdatedAt(inventory.getUpdatedAt());

        return dto;
    }

    // StockMovement Entity -> Response DTO
    public static StockMovementResponseDTO toStockMovementResponseDTO(StockMovement movement) {

        if (movement == null) {
            return null;
        }

        StockMovementResponseDTO dto = new StockMovementResponseDTO();

        dto.setMovementId(movement.getMovementId());

        dto.setProductId(movement.getProduct().getProductId());
        dto.setProductName(movement.getProduct().getProductName());

        dto.setWarehouseFrom(movement.getWarehouseFrom());
        dto.setWarehouseTo(movement.getWarehouseTo());

        dto.setQuantity(movement.getQuantity());

        dto.setMovementType(movement.getMovementType());

        dto.setUserId(movement.getUserId());

        dto.setCreatedAt(movement.getCreatedAt());

        return dto;
    }
}