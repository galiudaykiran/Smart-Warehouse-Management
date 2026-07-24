
package com.smart_warehouse_management.product_and_inventory.Service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.smart_warehouse_management.Authentication.Exception.DuplicateResourceException;
import com.smart_warehouse_management.product_and_inventory.Service.SupplierService;
import com.smart_warehouse_management.product_and_inventory.dto.SupplierRequestDTO;
import com.smart_warehouse_management.product_and_inventory.dto.SupplierResponseDTO;
import com.smart_warehouse_management.product_and_inventory.entity.Supplier;
import com.smart_warehouse_management.product_and_inventory.exception.SupplierNotFoundException;
import com.smart_warehouse_management.product_and_inventory.mapper.SupplierMapper;
import com.smart_warehouse_management.product_and_inventory.repository.SupplierRepository;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public SupplierResponseDTO createSupplier(SupplierRequestDTO dto) {

        if (supplierRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (supplierRepository.existsByGstNumber(dto.getGstNumber())) {
            throw new DuplicateResourceException("GST Number already exists");
        }

        Supplier supplier = SupplierMapper.toEntity(dto);

        Supplier savedSupplier = supplierRepository.save(supplier);

        return SupplierMapper.toResponseDTO(savedSupplier);
    }

    @Override
    public SupplierResponseDTO getSupplierById(Long id) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() ->
                        new SupplierNotFoundException("Supplier not found with id : " + id));

        return SupplierMapper.toResponseDTO(supplier);
    }

    @Override
    public Page<SupplierResponseDTO> getAllSuppliers(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return supplierRepository.findAll(pageable)
                .map(SupplierMapper::toResponseDTO);
    }

    @Override
    public SupplierResponseDTO updateSupplier(Long id,
                                              SupplierRequestDTO dto) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() ->
                        new SupplierNotFoundException("Supplier not found with id : " + id));

        if (!supplier.getEmail().equalsIgnoreCase(dto.getEmail())
                && supplierRepository.existsByEmail(dto.getEmail())) {

            throw new DuplicateResourceException("Email already exists");
        }

        if (!supplier.getGstNumber().equalsIgnoreCase(dto.getGstNumber())
                && supplierRepository.existsByGstNumber(dto.getGstNumber())) {

            throw new DuplicateResourceException("GST Number already exists");
        }

        supplier.setCompanyName(dto.getCompanyName());
        supplier.setContactPerson(dto.getContactPerson());
        supplier.setMobile(dto.getMobile());
        supplier.setEmail(dto.getEmail());
        supplier.setCity(dto.getCity());
        supplier.setGstNumber(dto.getGstNumber());

        Supplier updatedSupplier = supplierRepository.save(supplier);

        return SupplierMapper.toResponseDTO(updatedSupplier);
    }

    @Override
    public void deleteSupplier(Long id) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() ->
                        new SupplierNotFoundException("Supplier not found with id : " + id));

        supplierRepository.delete(supplier);
    }
}