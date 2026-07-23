package com.smart_warehouse_management.orders.controller;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.smart_warehouse_management.orders.dto.SalesOrderRequestDTO;
import com.smart_warehouse_management.orders.entity.SalesOrder;
import com.smart_warehouse_management.orders.serviceimpl.SalesOrderServiceImpl;



@RestController
@RequestMapping("/sales-orders")
public class SalesOrderController {

    private final SalesOrderServiceImpl salesOrderService;

    public SalesOrderController(SalesOrderServiceImpl salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalesOrder createSalesOrder(@RequestBody SalesOrderRequestDTO dto) {
        return salesOrderService.createSalesOrder(dto);
    }

    @GetMapping
    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderService.getAllSalesOrders();
    }

    @PutMapping("/{id}")
    public SalesOrder updateSalesOrder(@PathVariable Long id,
                                       @RequestBody SalesOrderRequestDTO dto) {
        return salesOrderService.updateSalesOrder(id, dto);
    }

    @PostMapping("/{id}/dispatch")
    public SalesOrder dispatchSalesOrder(@PathVariable Long id) {
        return salesOrderService.dispatchSalesOrder(id);
    }

    @PostMapping("/{id}/cancel")
    public SalesOrder cancelSalesOrder(@PathVariable Long id) {
        return salesOrderService.cancelSalesOrder(id);
    }
    @GetMapping("/reports/monthly")
    public Double getMonthlySales(
            @RequestParam Integer month,
            @RequestParam Integer year) {

        return salesOrderService.getMonthlySales(month, year);
    }
    @GetMapping("/revenue")
    public Double getTotalRevenue() {

        return salesOrderService.getTotalRevenue();

    }
}
