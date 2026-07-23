//package com.smart_warehouse_management.Product_And_Inventory.repository;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import com.smart_warehouse_management.Product_And_Inventory.entity.Product;
//
//@Repository
//public interface ProductRepository extends JpaRepository<Product, Long> {
//
//    Optional<Product> findBySku(String sku);
//
//    Optional<Product> findByBarcode(String barcode);
//
//    boolean existsBySku(String sku);
//
//    boolean existsByBarcode(String barcode);
//
//}


//package com.smart_warehouse_management.Product_And_Inventory.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//
//import com.smart_warehouse_management.Product_And_Inventory.entity.Product;
//
//public interface ProductRepository extends
//        JpaRepository<Product, Long>,
//        JpaSpecificationExecutor<Product> {
//
//    boolean existsBySku(String sku);
//
//    boolean existsByBarcode(String barcode);
//
//}


package com.smart_warehouse_management.Product_And_Inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smart_warehouse_management.Product_And_Inventory.entity.Product;

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