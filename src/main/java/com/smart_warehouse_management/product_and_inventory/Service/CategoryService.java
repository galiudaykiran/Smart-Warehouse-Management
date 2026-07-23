
package com.smart_warehouse_management.Product_And_Inventory.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.smart_warehouse_management.Product_And_Inventory.dto.CategoryRequestDTO;
import com.smart_warehouse_management.Product_And_Inventory.dto.CategoryResponseDTO;
import com.smart_warehouse_management.Product_And_Inventory.dto.ProductResponseDTO;

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