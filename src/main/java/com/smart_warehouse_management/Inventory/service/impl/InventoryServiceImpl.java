
package com.smart_warehouse_management.Inventory.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart_warehouse_management.Inventory.dto.AddStockRequestDTO;
import com.smart_warehouse_management.Inventory.dto.InventoryResponseDTO;
import com.smart_warehouse_management.Inventory.dto.RemoveStockRequestDTO;
import com.smart_warehouse_management.Inventory.dto.StockAdjustmentRequestDTO;
import com.smart_warehouse_management.Inventory.dto.StockMovementResponseDTO;
import com.smart_warehouse_management.Inventory.dto.TransferStockRequestDTO;
import com.smart_warehouse_management.Inventory.entity.Inventory;
import com.smart_warehouse_management.Inventory.entity.StockMovement;
import com.smart_warehouse_management.Inventory.enums.MovementType;
import com.smart_warehouse_management.Inventory.exception.InsufficientStockException;
import com.smart_warehouse_management.Inventory.mapper.InventoryMapper;
import com.smart_warehouse_management.Inventory.repository.InventoryRepository;
import com.smart_warehouse_management.Inventory.repository.StockMovementRepository;
import com.smart_warehouse_management.Inventory.service.InventoryService;
import com.smart_warehouse_management.product_and_inventory.entity.Product;
import com.smart_warehouse_management.product_and_inventory.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;

    @Override
    public InventoryResponseDTO addStock(AddStockRequestDTO dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Inventory inventory = inventoryRepository
                .findByWarehouseIdAndProduct_ProductId(
                        dto.getWarehouseId(),
                        dto.getProductId())
                .orElse(null);

        if (inventory == null) {

            inventory = new Inventory();
            inventory.setWarehouseId(dto.getWarehouseId());
            inventory.setProduct(product);
            inventory.setAvailableQuantity(dto.getQuantity());
            inventory.setReservedQuantity(0);
            inventory.setDamagedQuantity(0);

        } else {

            inventory.setAvailableQuantity(
                    inventory.getAvailableQuantity() + dto.getQuantity());
        }

        Inventory saved = inventoryRepository.save(inventory);

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setWarehouseFrom(0L);
        movement.setWarehouseTo(dto.getWarehouseId());
        movement.setQuantity(dto.getQuantity());
        movement.setMovementType(MovementType.IN);
        movement.setUserId(dto.getUserId());

        stockMovementRepository.save(movement);

        return InventoryMapper.toInventoryResponseDTO(saved);
    }

    @Override
    public InventoryResponseDTO removeStock(RemoveStockRequestDTO dto) {

        Inventory inventory = inventoryRepository
                .findByWarehouseIdAndProduct_ProductId(
                        dto.getWarehouseId(),
                        dto.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Inventory not found"));

        if (inventory.getAvailableQuantity() < dto.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock available");
        }

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - dto.getQuantity());

        Inventory savedInventory = inventoryRepository.save(inventory);

        StockMovement movement = new StockMovement();

        movement.setProduct(inventory.getProduct());
        movement.setWarehouseFrom(dto.getWarehouseId());
        movement.setWarehouseTo(0L);
        movement.setQuantity(dto.getQuantity());
        movement.setMovementType(MovementType.OUT);
        movement.setUserId(dto.getUserId());

        stockMovementRepository.save(movement);

        return InventoryMapper.toInventoryResponseDTO(savedInventory);
    }
    @Override
    @Transactional
    public void transferStock(TransferStockRequestDTO dto) {

        Inventory sourceInventory = inventoryRepository
                .findByWarehouseIdAndProduct_ProductId(
                        dto.getWarehouseFrom(),
                        dto.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Source inventory not found"));

        if (sourceInventory.getAvailableQuantity() < dto.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock for transfer");
        }

        Product product = sourceInventory.getProduct();

        Inventory destinationInventory = inventoryRepository
                .findByWarehouseIdAndProduct_ProductId(
                        dto.getWarehouseTo(),
                        dto.getProductId())
                .orElse(null);

        if (destinationInventory == null) {

            destinationInventory = new Inventory();
            destinationInventory.setWarehouseId(dto.getWarehouseTo());
            destinationInventory.setProduct(product);
            destinationInventory.setAvailableQuantity(0);
            destinationInventory.setReservedQuantity(0);
            destinationInventory.setDamagedQuantity(0);
        }

        // Remove stock from source
        sourceInventory.setAvailableQuantity(
                sourceInventory.getAvailableQuantity() - dto.getQuantity());

        // Add stock to destination
        destinationInventory.setAvailableQuantity(
                destinationInventory.getAvailableQuantity() + dto.getQuantity());

        inventoryRepository.save(sourceInventory);
        inventoryRepository.save(destinationInventory);

        StockMovement movement = new StockMovement();

        movement.setProduct(product);
        movement.setWarehouseFrom(dto.getWarehouseFrom());
        movement.setWarehouseTo(dto.getWarehouseTo());
        movement.setQuantity(dto.getQuantity());
        movement.setMovementType(MovementType.TRANSFER);
        movement.setUserId(dto.getUserId());

        stockMovementRepository.save(movement);
    }

    @Override
    public List<InventoryResponseDTO> getAllInventory() {

        return inventoryRepository.findAll()
                .stream()
                .map(InventoryMapper::toInventoryResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryResponseDTO> getInventoryByWarehouse(Long warehouseId) {

        return inventoryRepository.findByWarehouseId(warehouseId)
                .stream()
                .map(InventoryMapper::toInventoryResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryResponseDTO> getLowStockProducts() {

        return inventoryRepository.findByAvailableQuantityLessThanEqual(10)
                .stream()
                .map(InventoryMapper::toInventoryResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryResponseDTO> getOutOfStockProducts() {

        return inventoryRepository.findByAvailableQuantity(0)
                .stream()
                .map(InventoryMapper::toInventoryResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockMovementResponseDTO> getStockMovementHistory(Long productId) {

        return stockMovementRepository.findByProduct_ProductId(productId)
                .stream()
                .map(InventoryMapper::toStockMovementResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryResponseDTO stockAdjustment(StockAdjustmentRequestDTO dto) {

        Inventory inventory = inventoryRepository
                .findByWarehouseIdAndProduct_ProductId(
                        dto.getWarehouseId(),
                        dto.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Inventory not found"));

        inventory.setAvailableQuantity(dto.getAdjustedQuantity());

        Inventory savedInventory = inventoryRepository.save(inventory);

        StockMovement movement = new StockMovement();

        movement.setProduct(inventory.getProduct());
        movement.setWarehouseFrom(dto.getWarehouseId());
        movement.setWarehouseTo(dto.getWarehouseId());
        movement.setQuantity(dto.getAdjustedQuantity());
        movement.setMovementType(MovementType.IN);
        movement.setUserId(dto.getUserId());

        stockMovementRepository.save(movement);

        return InventoryMapper.toInventoryResponseDTO(savedInventory);
    }
}