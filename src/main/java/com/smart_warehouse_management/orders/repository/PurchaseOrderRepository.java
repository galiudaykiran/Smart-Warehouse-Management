package com.smart_warehouse_management.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smart_warehouse_management.orders.entity.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long>{

	@Query("""
			SELECT SUM(p.totalAmount)
			FROM PurchaseOrder p
			WHERE MONTH(p.createdAt)=:month
			AND YEAR(p.createdAt)=:year
			""")
			Double getMonthlyPurchase(Integer month,Integer year);
}
