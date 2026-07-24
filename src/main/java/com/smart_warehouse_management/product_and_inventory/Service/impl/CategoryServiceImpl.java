
package com.smart_warehouse_management.product_and_inventory.Service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.smart_warehouse_management.Authentication.Exception.DuplicateResourceException;
import com.smart_warehouse_management.product_and_inventory.Service.CategoryService;
import com.smart_warehouse_management.product_and_inventory.dto.CategoryRequestDTO;
import com.smart_warehouse_management.product_and_inventory.dto.CategoryResponseDTO;
import com.smart_warehouse_management.product_and_inventory.entity.Category;
import com.smart_warehouse_management.product_and_inventory.exception.CategoryNotFoundException;
import com.smart_warehouse_management.product_and_inventory.mapper.CategoryMapper;
import com.smart_warehouse_management.product_and_inventory.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {

        if (categoryRepository.existsByCategoryName(dto.getCategoryName())) {
            throw new DuplicateResourceException("Category already exists");
        }

        Category category = CategoryMapper.toEntity(dto);

        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toResponseDTO(savedCategory);
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found with id : " + id));

        return CategoryMapper.toResponseDTO(category);
    }

    @Override
    public Page<CategoryResponseDTO> getAllCategories(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return categoryRepository.findAll(pageable)
                .map(CategoryMapper::toResponseDTO);
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id,
                                              CategoryRequestDTO dto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found with id : " + id));

        if (!category.getCategoryName().equalsIgnoreCase(dto.getCategoryName())
                && categoryRepository.existsByCategoryName(dto.getCategoryName())) {

            throw new DuplicateResourceException("Category already exists");
        }

        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());

        Category updatedCategory = categoryRepository.save(category);

        return CategoryMapper.toResponseDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found with id : " + id));

        categoryRepository.delete(category);
    }

	
    
   

}