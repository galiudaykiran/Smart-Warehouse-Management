
package com.smart_warehouse_management.product_and_inventory.Service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smart_warehouse_management.product_and_inventory.Service.ProductService;
import com.smart_warehouse_management.product_and_inventory.dto.ProductRequestDTO;
import com.smart_warehouse_management.product_and_inventory.dto.ProductResponseDTO;
import com.smart_warehouse_management.product_and_inventory.entity.Category;
import com.smart_warehouse_management.product_and_inventory.entity.Product;
import com.smart_warehouse_management.product_and_inventory.entity.Supplier;
import com.smart_warehouse_management.product_and_inventory.exception.CategoryNotFoundException;
import com.smart_warehouse_management.product_and_inventory.exception.ProductNotFoundException;
import com.smart_warehouse_management.product_and_inventory.exception.SupplierNotFoundException;
import com.smart_warehouse_management.product_and_inventory.mapper.ProductMapper;
import com.smart_warehouse_management.product_and_inventory.repository.CategoryRepository;
import com.smart_warehouse_management.product_and_inventory.repository.ProductRepository;
import com.smart_warehouse_management.product_and_inventory.repository.SupplierRepository;
import com.smart_warehouse_management.product_and_inventory.specification.ProductSpecification;
import com.smart_warehouse_management.product_and_inventory.util.BarcodeGenerator;
import com.smart_warehouse_management.product_and_inventory.util.FileUploadUtil;
import com.smart_warehouse_management.product_and_inventory.util.SkuGenerator;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final SkuGenerator skuGenerator;
    private final BarcodeGenerator barcodeGenerator;
    private final FileUploadUtil fileUploadUtil;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              SupplierRepository supplierRepository,
                              SkuGenerator skuGenerator,
                              BarcodeGenerator barcodeGenerator, FileUploadUtil fileUploadUtil) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.skuGenerator = skuGenerator;
        this.barcodeGenerator = barcodeGenerator;
		this.fileUploadUtil = fileUploadUtil;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found"));

        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() ->
                        new SupplierNotFoundException("Supplier not found"));

        Product product = ProductMapper.toEntity(dto);

        product.setCategory(category);
        product.setSupplier(supplier);

        product.setSku(skuGenerator.generateSku(category));
        product.setBarcode(barcodeGenerator.generateBarcode());

        product.setActive(true);

        Product savedProduct = productRepository.save(product);

        return ProductMapper.toResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id : " + id));

        return ProductMapper.toResponseDTO(product);
    }

    @Override
    public Page<ProductResponseDTO> getAllProducts(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(pageable)
                .map(ProductMapper::toResponseDTO);
    }

    @Override
    public ProductResponseDTO updateProduct(
            Long id,
            ProductRequestDTO dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id : " + id));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found"));

        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() ->
                        new SupplierNotFoundException("Supplier not found"));

        product.setProductName(dto.getProductName());
        product.setCategory(category);
        product.setSupplier(supplier);
        product.setPrice(dto.getPrice());
        product.setCostPrice(dto.getCostPrice());
        product.setMinimumStock(dto.getMinimumStock());

        Product updatedProduct = productRepository.save(product);

        return ProductMapper.toResponseDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id : " + id));

        productRepository.delete(product);
    }
    
    @Override
    public Page<ProductResponseDTO> filterProducts(

            String productName,

            String category,

            String supplier,

            Double minPrice,

            Double maxPrice,

            int page,

            int size,

            String sortBy,

            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Product> specification =
                Specification.where(ProductSpecification.hasProductName(productName))
                        .and(ProductSpecification.hasCategory(category))
                        .and(ProductSpecification.hasSupplier(supplier))
                        .and(ProductSpecification.minPrice(minPrice))
                        .and(ProductSpecification.maxPrice(maxPrice));

        return productRepository
                .findAll(specification, pageable)
                .map(ProductMapper::toResponseDTO);
    }

    @Override
    public String uploadProductImage(Long productId,
                                     MultipartFile file) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found"));

        try {

            String imageUrl =
                    fileUploadUtil.uploadFile(file);

            product.setImageUrl(imageUrl);

            productRepository.save(product);

            return imageUrl;

        } catch (Exception e) {

            throw new RuntimeException("Image upload failed");

        }
    }
    @Override
    public List<ProductResponseDTO> getLowStockProducts() {

        return productRepository
                .getLowStockProducts()
                .stream()
                .map(ProductMapper::toResponseDTO)
                .toList();
    }
    
    @Override
    public List<ProductResponseDTO> searchByProductName(String productName) {

        return productRepository
                .findByProductNameContainingIgnoreCase(productName)
                .stream()
                .map(ProductMapper::toResponseDTO)
                .toList();
    }

}