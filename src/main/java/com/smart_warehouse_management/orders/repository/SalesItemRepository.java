package com.smart_warehouse_management.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_warehouse_management.orders.entity.SalesItem;

public interface SalesItemRepository extends JpaRepository<SalesItem, Long>{

}
