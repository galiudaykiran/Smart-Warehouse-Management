package com.smart_warehouse_management.orders.dto;

import java.util.List;

import lombok.Data;

@Data
public class PurchaseOrderRequestDTO {

    private Long supplierId;

    private List<PurchaseItemDTO> items;

}
