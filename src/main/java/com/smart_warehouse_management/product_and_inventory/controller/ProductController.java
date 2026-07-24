
package com.smart_warehouse_management.product_and_inventory.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smart_warehouse_management.product_and_inventory.Service.ProductService;
import com.smart_warehouse_management.product_and_inventory.dto.ProductRequestDTO;
import com.smart_warehouse_management.product_and_inventory.dto.ProductResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create Product
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO dto) {

        ProductResponseDTO response = productService.createProduct(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(

            @PathVariable Long id,

            @RequestParam("file") MultipartFile file) {

        String imageUrl =
                productService.uploadProductImage(id, file);

        return ResponseEntity.ok(imageUrl);
    }

    // Get Product By Id
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @PathVariable Long id) {

        return ResponseEntity.ok(productService.getProductById(id));
    }

    // Get All Products with Pagination & Sorting
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "productName") String sortBy,

            @RequestParam(defaultValue = "asc") String sortDir) {

        return ResponseEntity.ok(
                productService.getAllProducts(
                        page,
                        size,
                        sortBy,
                        sortDir));
    }

    // Update Product
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO dto) {

        return ResponseEntity.ok(
                productService.updateProduct(id, dto));
    }

    // Delete Product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok("Product deleted successfully");
    }
    
    
    
    
    @GetMapping("/filter")
    public ResponseEntity<Page<ProductResponseDTO>> filterProducts(

            @RequestParam(required = false) String productName,

            @RequestParam(required = false) String category,

            @RequestParam(required = false) String supplier,

            @RequestParam(required = false) Double minPrice,

            @RequestParam(required = false) Double maxPrice,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "productName") String sortBy,

            @RequestParam(defaultValue = "asc") String sortDir){

        return ResponseEntity.ok(

                productService.filterProducts(

                        productName,

                        category,

                        supplier,

                        minPrice,

                        maxPrice,

                        page,

                        size,

                        sortBy,

                        sortDir));
        
    }
    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductResponseDTO>> getLowStockProducts() {

        return ResponseEntity.ok(
                productService.getLowStockProducts());
    }
    
    
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProduct(

            @RequestParam String productName){

        return ResponseEntity.ok(
                productService.searchByProductName(productName));
    }
}