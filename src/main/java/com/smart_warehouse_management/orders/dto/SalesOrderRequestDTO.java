package com.smart_warehouse_management.orders.dto;

import java.util.List;

import lombok.Data;

@Data
public class SalesOrderRequestDTO {

    private String customerName;

    private String mobile;

    private List<SalesItemDTO> items;

}
