
package com.smart_warehouse_management.Inventory.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smart_warehouse_management.Inventory.entity.Inventory;
import com.smart_warehouse_management.Inventory.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LowStockScheduler {

    private static final Logger logger =
            LoggerFactory.getLogger(LowStockScheduler.class);

    private final InventoryRepository inventoryRepository;

    /**
     * Runs every day at 9:00 AM
     */
    @Scheduled(cron = "0 0 9 * * *")
    public void checkLowStock() {

        logger.info("Checking Low Stock Products...");

        List<Inventory> inventories =
                inventoryRepository.findByAvailableQuantityLessThanEqual(10);

        if (inventories.isEmpty()) {

            logger.info("No Low Stock Products Found.");

            return;
        }

        logger.info("Low Stock Products:");

        for (Inventory inventory : inventories) {

            logger.warn(
                    "Product : {} | Warehouse : {} | Available Stock : {}",
                    inventory.getProduct().getProductName(),
                    inventory.getWarehouseId(),
                    inventory.getAvailableQuantity());

        }

    }

}