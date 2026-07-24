package com.smart_warehouse_management.orders.controller;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.smart_warehouse_management.orders.dto.PurchaseOrderRequestDTO;
import com.smart_warehouse_management.orders.entity.PurchaseOrder;
import com.smart_warehouse_management.orders.serviceimpl.PurchaseOrderServiceImpl;



@RestController
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderServiceImpl purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderServiceImpl purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseOrder createPurchaseOrder(@RequestBody PurchaseOrderRequestDTO dto) {
        return purchaseOrderService.createPurchaseOrder(dto);
    }

    @GetMapping
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderService.getAllPurchaseOrders();
    }

    @PutMapping("/{id}")
    public PurchaseOrder updatePurchaseOrder(@PathVariable Long id,
                                             @RequestBody PurchaseOrderRequestDTO dto) {
        return purchaseOrderService.updatePurchaseOrder(id, dto);
    }

    @PostMapping("/{id}/approve")
    public PurchaseOrder approvePurchaseOrder(@PathVariable Long id) {
        return purchaseOrderService.approvePurchaseOrder(id);
    }

    @PostMapping("/{id}/reject")
    public PurchaseOrder rejectPurchaseOrder(@PathVariable Long id) {
        return purchaseOrderService.rejectPurchaseOrder(id);
    }
    @GetMapping("/reports/monthly")
    public Double getMonthlyPurchase(
            @RequestParam Integer month,
            @RequestParam Integer year) {

        return purchaseOrderService.getMonthlyPurchase(month, year);
    }
}