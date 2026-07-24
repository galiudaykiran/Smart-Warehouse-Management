
package com.smart_warehouse_management.product_and_inventory.util;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import com.smart_warehouse_management.product_and_inventory.repository.ProductRepository;

@Component
public class BarcodeGenerator {

    private final ProductRepository productRepository;

    public BarcodeGenerator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String generateBarcode() {

        String barcode;

        do {

            barcode = String.valueOf(
                    ThreadLocalRandom.current()
                            .nextLong(1000000000000L, 9999999999999L));

        } while (productRepository.existsByBarcode(barcode));

        return barcode;
    }
}