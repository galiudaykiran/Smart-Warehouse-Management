package com.smart_warehouse_management.Product_And_Inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_warehouse_management.Product_And_Inventory.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findByEmail(String email);

    Optional<Supplier> findByGstNumber(String gstNumber);

    boolean existsByEmail(String email);

    boolean existsByGstNumber(String gstNumber);

}