
package com.smart_warehouse_management.Product_And_Inventory.util;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import com.smart_warehouse_management.Product_And_Inventory.entity.Category;
import com.smart_warehouse_management.Product_And_Inventory.repository.ProductRepository;

@Component
public class SkuGenerator {

    private final ProductRepository productRepository;

    public SkuGenerator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String generateSku(Category category) {

        String prefix = category.getCategoryName()
                .substring(0, Math.min(3, category.getCategoryName().length()))
                .toUpperCase();

        String sku;

        do {

            int number = ThreadLocalRandom.current()
                    .nextInt(1000, 9999);

            sku = prefix + "-" + number;

        } while (productRepository.existsBySku(sku));

        return sku;
    }
}