package com.smart_warehouse_management.orders.dto;


import lombok.Data;

@Data
public class SalesItemDTO {

    private Long productId;

    private Integer quantity;

    private Double sellingPrice;

}