
package com.smart_warehouse_management.Product_And_Inventory.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.smart_warehouse_management.Product_And_Inventory.dto.ProductRequestDTO;
import com.smart_warehouse_management.Product_And_Inventory.dto.ProductResponseDTO;
import org.springframework.web.multipart.MultipartFile;

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
