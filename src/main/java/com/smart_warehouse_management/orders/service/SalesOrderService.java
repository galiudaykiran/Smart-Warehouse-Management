package com.smart_warehouse_management.orders.service;

import java.util.List;

import com.smart_warehouse_management.orders.dto.SalesOrderRequestDTO;
import com.smart_warehouse_management.orders.entity.SalesOrder;

public interface SalesOrderService {
	SalesOrder createSalesOrder(SalesOrderRequestDTO dto);

    List<SalesOrder> getAllSalesOrders();

    SalesOrder updateSalesOrder(Long id, SalesOrderRequestDTO dto);

    SalesOrder dispatchSalesOrder(Long id);

    SalesOrder cancelSalesOrder(Long id);
    Double getMonthlySales(Integer month, Integer year);
    Double getTotalRevenue();

}
