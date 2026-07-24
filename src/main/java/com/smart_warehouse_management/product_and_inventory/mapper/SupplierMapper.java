
package com.smart_warehouse_management.product_and_inventory.mapper;

import com.smart_warehouse_management.product_and_inventory.dto.SupplierRequestDTO;
import com.smart_warehouse_management.product_and_inventory.dto.SupplierResponseDTO;
import com.smart_warehouse_management.product_and_inventory.entity.Supplier;

public class SupplierMapper {

    // RequestDTO -> Entity
    public static Supplier toEntity(SupplierRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        Supplier supplier = new Supplier();

        supplier.setCompanyName(dto.getCompanyName());
        supplier.setContactPerson(dto.getContactPerson());
        supplier.setMobile(dto.getMobile());
        supplier.setEmail(dto.getEmail());
        supplier.setCity(dto.getCity());
        supplier.setGstNumber(dto.getGstNumber());

        // Default value
        supplier.setActive(true);

        return supplier;
    }

    // Entity -> ResponseDTO
    public static SupplierResponseDTO toResponseDTO(Supplier supplier) {

        if (supplier == null) {
            return null;
        }

        SupplierResponseDTO dto = new SupplierResponseDTO();

        dto.setSupplierId(supplier.getSupplierId());
        dto.setCompanyName(supplier.getCompanyName());
        dto.setContactPerson(supplier.getContactPerson());
        dto.setMobile(supplier.getMobile());
        dto.setEmail(supplier.getEmail());
        dto.setCity(supplier.getCity());
        dto.setGstNumber(supplier.getGstNumber());
        dto.setActive(supplier.getActive());

        return dto;
    }
}