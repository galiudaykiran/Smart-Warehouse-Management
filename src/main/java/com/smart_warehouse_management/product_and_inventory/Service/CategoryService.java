
package com.smart_warehouse_management.product_and_inventory.Service;

import org.springframework.data.domain.Page;

import com.smart_warehouse_management.product_and_inventory.dto.CategoryRequestDTO;
import com.smart_warehouse_management.product_and_inventory.dto.CategoryResponseDTO;

public interface CategoryService {

    CategoryResponseDTO createCategory(CategoryRequestDTO dto);

    CategoryResponseDTO getCategoryById(Long id);

    Page<CategoryResponseDTO> getAllCategories(
            int page,
            int size,
            String sortBy,
            String sortDir);

    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto);

    void deleteCategory(Long id);

	
}