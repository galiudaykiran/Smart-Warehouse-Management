package com.smart_warehouse_management.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_warehouse_management.orders.entity.PurchaseItem;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long>{

}
