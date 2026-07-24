
package com.smart_warehouse_management.Inventory.service;

import java.util.List;

import com.smart_warehouse_management.Inventory.dto.AddStockRequestDTO;
import com.smart_warehouse_management.Inventory.dto.InventoryResponseDTO;
import com.smart_warehouse_management.Inventory.dto.RemoveStockRequestDTO;
import com.smart_warehouse_management.Inventory.dto.StockAdjustmentRequestDTO;
import com.smart_warehouse_management.Inventory.dto.StockMovementResponseDTO;
import com.smart_warehouse_management.Inventory.dto.TransferStockRequestDTO;

public interface InventoryService {

    InventoryResponseDTO addStock(AddStockRequestDTO dto);

    InventoryResponseDTO removeStock(RemoveStockRequestDTO dto);

    void transferStock(TransferStockRequestDTO dto);

    List<InventoryResponseDTO> getAllInventory();

    List<InventoryResponseDTO> getInventoryByWarehouse(Long warehouseId);

    List<InventoryResponseDTO> getLowStockProducts();

    List<InventoryResponseDTO> getOutOfStockProducts();

    List<StockMovementResponseDTO> getStockMovementHistory(Long productId);

    InventoryResponseDTO stockAdjustment(StockAdjustmentRequestDTO dto);

}