
package com.smart_warehouse_management.product_and_inventory.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.smart_warehouse_management.product_and_inventory.dto.ProductRequestDTO;
import com.smart_warehouse_management.product_and_inventory.dto.ProductResponseDTO;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO dto);

    ProductResponseDTO getProductById(Long id);

    Page<ProductResponseDTO> getAllProducts(
            int page,
            int size,
            String sortBy,
            String sortDir);

    ProductResponseDTO updateProduct(
            Long id,
            ProductRequestDTO dto);

    void deleteProduct(Long id);
    
    
    
    Page<ProductResponseDTO> filterProducts(

            String productName,

            String category,

            String supplier,

            Double minPrice,

            Double maxPrice,

            int page,

            int size,

            String sortBy,

            String sortDir);




   String uploadProductImage(Long productId,
                          MultipartFile file);
   
   
   List<ProductResponseDTO> getLowStockProducts();
   
   List<ProductResponseDTO> searchByProductName(String productName);
}
