package com.smart_warehouse_management.Inventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_warehouse_management.Inventory.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByWarehouseIdAndProduct_ProductId(Long warehouseId, Long productId);

    List<Inventory> findByWarehouseId(Long warehouseId);

    List<Inventory> findByAvailableQuantityLessThanEqual(Integer quantity);

    List<Inventory> findByAvailableQuantity(Integer quantity);

}