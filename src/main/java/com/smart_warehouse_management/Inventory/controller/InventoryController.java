
package com.smart_warehouse_management.Inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart_warehouse_management.Inventory.dto.AddStockRequestDTO;
import com.smart_warehouse_management.Inventory.dto.InventoryResponseDTO;
import com.smart_warehouse_management.Inventory.dto.RemoveStockRequestDTO;
import com.smart_warehouse_management.Inventory.dto.StockAdjustmentRequestDTO;
import com.smart_warehouse_management.Inventory.dto.StockMovementResponseDTO;
import com.smart_warehouse_management.Inventory.dto.TransferStockRequestDTO;
import com.smart_warehouse_management.Inventory.service.InventoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // Add Stock
    @PostMapping("/add-stock")
    public ResponseEntity<InventoryResponseDTO> addStock(
            @Valid @RequestBody AddStockRequestDTO dto) {

        return new ResponseEntity<>(
                inventoryService.addStock(dto),
                HttpStatus.CREATED);
    }

    // Remove Stock
    @PostMapping("/remove-stock")
    public ResponseEntity<InventoryResponseDTO> removeStock(
            @Valid @RequestBody RemoveStockRequestDTO dto) {

        return ResponseEntity.ok(
                inventoryService.removeStock(dto));
    }

    // Transfer Stock
    @PostMapping("/transfer")
    public ResponseEntity<String> transferStock(
            @Valid @RequestBody TransferStockRequestDTO dto) {

        inventoryService.transferStock(dto);

        return ResponseEntity.ok("Stock transferred successfully");
    }

    // Get All Inventory
    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventory() {

        return ResponseEntity.ok(
                inventoryService.getAllInventory());
    }

    // Get Inventory by Warehouse
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<InventoryResponseDTO>> getInventoryByWarehouse(
            @PathVariable Long warehouseId) {

        return ResponseEntity.ok(
                inventoryService.getInventoryByWarehouse(warehouseId));
    }

    // Low Stock
    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryResponseDTO>> getLowStockProducts() {

        return ResponseEntity.ok(
                inventoryService.getLowStockProducts());
    }

    // Out Of Stock
    @GetMapping("/out-of-stock")
    public ResponseEntity<List<InventoryResponseDTO>> getOutOfStockProducts() {

        return ResponseEntity.ok(
                inventoryService.getOutOfStockProducts());
    }

    // Stock History
    @GetMapping("/history/{productId}")
    public ResponseEntity<List<StockMovementResponseDTO>> getStockHistory(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                inventoryService.getStockMovementHistory(productId));
    }

    // Stock Adjustment
    @PostMapping("/adjustment")
    public ResponseEntity<InventoryResponseDTO> stockAdjustment(
            @Valid @RequestBody StockAdjustmentRequestDTO dto) {

        return ResponseEntity.ok(
                inventoryService.stockAdjustment(dto));
    }

}