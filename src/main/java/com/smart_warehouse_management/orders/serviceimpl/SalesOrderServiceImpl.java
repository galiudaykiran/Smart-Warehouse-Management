package com.smart_warehouse_management.orders.serviceimpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.smart_warehouse_management.Authentication.Dto.UserResponseDto;
import com.smart_warehouse_management.Authentication.Service.AuthService;
import com.smart_warehouse_management.Inventory.dto.RemoveStockRequestDTO;
import com.smart_warehouse_management.Inventory.service.InventoryService;
import com.smart_warehouse_management.orders.dto.SalesItemDTO;
import com.smart_warehouse_management.orders.dto.SalesOrderRequestDTO;
import com.smart_warehouse_management.orders.entity.SalesItem;
import com.smart_warehouse_management.orders.entity.SalesOrder;
import com.smart_warehouse_management.orders.enums.OrderStatus;
import com.smart_warehouse_management.orders.exception.InvalidOrderStatusException;
import com.smart_warehouse_management.orders.exception.ResourceIsNotFoundException;
import com.smart_warehouse_management.orders.repository.SalesOrderRepository;
import com.smart_warehouse_management.orders.service.*;

@Service
public class SalesOrderServiceImpl implements SalesOrderService{
	
	 private final SalesOrderRepository salesOrderRepository;
	 private final InventoryService inventoryService;
	 private final AuthService authService;

	    public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository,InventoryService inventoryService,AuthService authService) {
	        this.salesOrderRepository = salesOrderRepository;
	        this.inventoryService = inventoryService;
	        this.authService=authService;

	    }

	    @Override
	    public SalesOrder createSalesOrder(SalesOrderRequestDTO dto) {

	        SalesOrder order = new SalesOrder();

	        order.setCustomerName(dto.getCustomerName());
	        order.setMobile(dto.getMobile());
	        order.setCreatedAt(LocalDateTime.now());
	        order.setStatus(OrderStatus.PENDING);

	        List<SalesItem> items = new ArrayList<>();

	        double total = 0;

	        for (SalesItemDTO itemDto : dto.getItems()) {

	            SalesItem item = new SalesItem();

	            item.setSalesOrder(order);

	            item.setProductId(itemDto.getProductId());

	            item.setQuantity(itemDto.getQuantity());

	            item.setPrice(itemDto.getSellingPrice());

	            total += itemDto.getQuantity() * itemDto.getSellingPrice();

	            items.add(item);
	        }

	        order.setItems(items);

	        order.setTotalAmount(total);

	        return salesOrderRepository.save(order);
	    }

	   
	    public List<SalesOrder> getAllSalesOrders() {
	        return salesOrderRepository.findAll();
	    }

	    
	    public SalesOrder updateSalesOrder(Long id, SalesOrderRequestDTO dto) {

	        SalesOrder order = salesOrderRepository.findById(id).orElseThrow(() ->
            new ResourceIsNotFoundException(
	                "Purchase Order not found with id " + id));


	        order.setCustomerName(dto.getCustomerName());

	        return salesOrderRepository.save(order);
	    }
	    @Override
	    public SalesOrder dispatchSalesOrder(Long id) {

	        SalesOrder order = salesOrderRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceIsNotFoundException(
	                                "Sales Order not found with id " + id));

	        // Only PENDING orders can be dispatched
	        if (order.getStatus() != OrderStatus.PENDING) {
	            throw new InvalidOrderStatusException(
	                    "Only PENDING orders can be dispatched");
	        }

	        // Get logged-in user
	        Authentication authentication =
	                SecurityContextHolder.getContext().getAuthentication();

	        String email = authentication.getName();

	        UserResponseDto user = authService.getUserByEmail(email);

	        // Update inventory for every product in the order
	        for (SalesItem item : order.getItems()) {

	            RemoveStockRequestDTO dto = new RemoveStockRequestDTO();

	            dto.setWarehouseId(1L);              // Replace with actual warehouse later
	            dto.setProductId(item.getProductId());
	            dto.setQuantity(item.getQuantity());
	            dto.setUserId(user.getId());

	            // Debugging
	            System.out.println("Dispatching Product : " + item.getProductId());
	            System.out.println("Quantity            : " + item.getQuantity());
	            System.out.println("Warehouse           : " + dto.getWarehouseId());

	            inventoryService.removeStock(dto);

	            System.out.println("Inventory Updated Successfully");
	        }

	        order.setStatus(OrderStatus.DISPATCHED);

	        return salesOrderRepository.save(order);
	    }

	    
//	    public SalesOrder dispatchSalesOrder(Long id) {
//
//	        SalesOrder order = salesOrderRepository.findById(id).orElseThrow();
//
//	        order.setStatus(OrderStatus.DISPATCHED);
//	        for (SalesItem item : order.getItems()) {
//
//	            RemoveStockRequestDTO dto = new RemoveStockRequestDTO();
//
////	            dto.setUserId(1L);
////	            dto.setProductId(item.getProductId());
////	            dto.setQuantity(item.getQuantity());
////	            dto.setUserId(1L);
//	            Authentication authentication =
//		                SecurityContextHolder.getContext().getAuthentication();
//
//		        String email = authentication.getName();
//
//		        UserResponseDto user = authService.getUserByEmail(email);
//
//		        dto.setUserId(user.getId());
//	            dto.setWarehouseId(1L);      
//	            dto.setProductId(item.getProductId());
//	            dto.setQuantity(item.getQuantity());
//	            //dto.setUserId(1L);   
//
//	            inventoryService.removeStock(dto);
//	        }
//
//	        return salesOrderRepository.save(order);
//	    }

	    
	    public SalesOrder cancelSalesOrder(Long id) {

	        SalesOrder order = salesOrderRepository.findById(id).orElseThrow();

	        order.setStatus(OrderStatus.CANCELLED);

	        return salesOrderRepository.save(order);
	    }
	    @Override
	    public Double getMonthlySales(Integer month, Integer year) {
	        return salesOrderRepository.getMonthlySales(month, year);
	    }
	    @Override
	    public Double getTotalRevenue() {

	        return salesOrderRepository.getTotalRevenue();

	    }
}
