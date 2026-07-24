
package com.smart_warehouse_management.Inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_warehouse_management.Inventory.entity.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findByProduct_ProductId(Long productId);

    List<StockMovement> findByWarehouseFrom(Long warehouseFrom);

    List<StockMovement> findByWarehouseTo(Long warehouseTo);

}