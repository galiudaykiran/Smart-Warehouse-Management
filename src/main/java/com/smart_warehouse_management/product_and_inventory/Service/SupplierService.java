
package com.smart_warehouse_management.product_and_inventory.Service;

import org.springframework.data.domain.Page;

import com.smart_warehouse_management.product_and_inventory.dto.SupplierRequestDTO;
import com.smart_warehouse_management.product_and_inventory.dto.SupplierResponseDTO;

public interface SupplierService {

    SupplierResponseDTO createSupplier(SupplierRequestDTO dto);

    SupplierResponseDTO getSupplierById(Long id);

    Page<SupplierResponseDTO> getAllSuppliers(
            int page,
            int size,
            String sortBy,
            String sortDir);

    SupplierResponseDTO updateSupplier(Long id,
                                       SupplierRequestDTO dto);

    void deleteSupplier(Long id);

}