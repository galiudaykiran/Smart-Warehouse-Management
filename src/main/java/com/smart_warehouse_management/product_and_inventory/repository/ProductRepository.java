

package com.smart_warehouse_management.product_and_inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smart_warehouse_management.product_and_inventory.entity.Product;

@Repository
public interface ProductRepository extends
        JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {

    boolean existsBySku(String sku);

    boolean existsByBarcode(String barcode);
    
    List<Product> findByCurrentStockLessThanEqual(Integer minimumStock);
    @Query("""
    	       SELECT p
    	       FROM Product p
    	       WHERE p.currentStock <= p.minimumStock
    	       """)
    	List<Product> getLowStockProducts();
    
    
    List<Product> findByProductNameContainingIgnoreCase(String productName);
}