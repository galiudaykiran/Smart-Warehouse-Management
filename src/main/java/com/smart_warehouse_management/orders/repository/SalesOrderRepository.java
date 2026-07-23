package com.smart_warehouse_management.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smart_warehouse_management.orders.entity.SalesOrder;

public interface SalesOrderRepository extends  JpaRepository<SalesOrder, Long>{
	@Query("""
			SELECT SUM(s.totalAmount)
			FROM SalesOrder s
			WHERE MONTH(s.createdAt)=:month
			AND YEAR(s.createdAt)=:year
			""")
			Double getMonthlySales(Integer month,Integer year);
	
	@Query("SELECT SUM(s.totalAmount) FROM SalesOrder s")
	Double getTotalRevenue();

}
