
package com.smart_warehouse_management.Product_And_Inventory.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {

    private Long productId;
    private String productName;
    private String sku;
    private String categoryName;
    private String supplierName;
    private Double price;
    private Double costPrice;
    private Integer minimumStock;
    private String barcode;
    private Boolean active;
    private String imageUrl;
    private Integer currentStock;
    private String stockStatus;
	
}