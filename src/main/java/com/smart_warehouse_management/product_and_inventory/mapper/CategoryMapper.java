
package com.smart_warehouse_management.product_and_inventory.mapper;

import com.smart_warehouse_management.product_and_inventory.dto.CategoryRequestDTO;
import com.smart_warehouse_management.product_and_inventory.dto.CategoryResponseDTO;
import com.smart_warehouse_management.product_and_inventory.entity.Category;

public class CategoryMapper {

    // RequestDTO -> Entity
    public static Category toEntity(CategoryRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        Category category = new Category();
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());

        return category;
    }

    // Entity -> ResponseDTO
    public static CategoryResponseDTO toResponseDTO(Category category) {

        if (category == null) {
            return null;
        }

        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());

        return dto;
    }
}