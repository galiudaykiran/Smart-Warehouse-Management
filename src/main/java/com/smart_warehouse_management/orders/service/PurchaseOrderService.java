package com.smart_warehouse_management.orders.service;

import java.util.List;

import com.smart_warehouse_management.orders.dto.PurchaseOrderRequestDTO;
import com.smart_warehouse_management.orders.entity.PurchaseOrder;

public interface PurchaseOrderService {

    PurchaseOrder createPurchaseOrder(PurchaseOrderRequestDTO dto);

    List<PurchaseOrder> getAllPurchaseOrders();

    PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrderRequestDTO dto);

    PurchaseOrder approvePurchaseOrder(Long id);

    PurchaseOrder rejectPurchaseOrder(Long id);
   

    Double getMonthlyPurchase(Integer month,Integer year);
}