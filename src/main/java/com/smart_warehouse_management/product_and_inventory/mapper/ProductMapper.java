
package com.smart_warehouse_management.Product_And_Inventory.mapper;

import com.smart_warehouse_management.Product_And_Inventory.dto.ProductRequestDTO;
import com.smart_warehouse_management.Product_And_Inventory.dto.ProductResponseDTO;
import com.smart_warehouse_management.Product_And_Inventory.entity.Product;

public class ProductMapper {

    // RequestDTO -> Entity
    public static Product toEntity(ProductRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        Product product = new Product();

        product.setProductName(dto.getProductName());
        product.setPrice(dto.getPrice());
        product.setCostPrice(dto.getCostPrice());
        product.setMinimumStock(dto.getMinimumStock());
        product.setCurrentStock(dto.getCurrentStock());

        /*
         * Don't set these here:
         *
         * Category
         * Supplier
         * SKU
         * Barcode
         * ImageUrl
         *
         * They are handled in ProductService.
         */

        return product;
    }

    // Entity -> ResponseDTO
    public static ProductResponseDTO toResponseDTO(Product product) {

        if (product == null) {
            return null;
        }

        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setSku(product.getSku());

        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getCategoryName());
        }

        if (product.getSupplier() != null) {
            dto.setSupplierName(product.getSupplier().getCompanyName());
        }
        
        if (product.getCurrentStock() <= product.getMinimumStock()) {
            dto.setStockStatus("LOW");
        } else {
            dto.setStockStatus("AVAILABLE");
        }

        dto.setPrice(product.getPrice());
        dto.setCostPrice(product.getCostPrice());
        dto.setMinimumStock(product.getMinimumStock());
        dto.setBarcode(product.getBarcode());
        dto.setActive(product.getActive());
        dto.setImageUrl(product.getImageUrl());
        dto.setCurrentStock(product.getCurrentStock());

        return dto;
    }
}