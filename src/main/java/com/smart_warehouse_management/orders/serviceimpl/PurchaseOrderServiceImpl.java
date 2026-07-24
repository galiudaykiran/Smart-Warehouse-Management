package com.smart_warehouse_management.orders.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smart_warehouse_management.orders.dto.PurchaseItemDTO;
import com.smart_warehouse_management.orders.dto.PurchaseOrderRequestDTO;
import com.smart_warehouse_management.orders.entity.PurchaseItem;
import com.smart_warehouse_management.orders.entity.PurchaseOrder;
import com.smart_warehouse_management.orders.enums.OrderStatus;
import com.smart_warehouse_management.orders.exception.InvalidOrderStatusException;
import com.smart_warehouse_management.orders.exception.ResourceIsNotFoundException;
import com.smart_warehouse_management.orders.repository.PurchaseOrderRepository;
import com.smart_warehouse_management.orders.service.*;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

	   private final PurchaseOrderRepository purchaseOrderRepository;

	    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository) {
	        this.purchaseOrderRepository = purchaseOrderRepository;
	    }

	   
	    
	    @Override
	    public PurchaseOrder createPurchaseOrder(PurchaseOrderRequestDTO dto) {

	        PurchaseOrder order = new PurchaseOrder();

	        order.setSupplierId(dto.getSupplierId());

	        order.setCreatedAt(LocalDateTime.now());

	        order.setCreatedBy(1L); 

	        order.setStatus(OrderStatus.APPROVED);

	        List<PurchaseItem> items = new ArrayList<>();

	        double total = 0;

	        for (PurchaseItemDTO itemDto : dto.getItems()) {

	            PurchaseItem item = new PurchaseItem();

	            item.setPurchaseOrder(order);

	            item.setProductId(itemDto.getProductId());

	            item.setQuantity(itemDto.getQuantity());

	            item.setPrice(itemDto.getPrice());

	            total += itemDto.getQuantity() * itemDto.getPrice();

	            items.add(item);
	        }

	        order.setItems(items);

	        order.setTotalAmount(total);

	        return purchaseOrderRepository.save(order);
	    }
	   
	    public List<PurchaseOrder> getAllPurchaseOrders() {
	        return purchaseOrderRepository.findAll();
	    }

	    
	    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrderRequestDTO dto) {

	    	PurchaseOrder order = purchaseOrderRepository.findById(id)
	    	        .orElseThrow(() ->
	    	            new ResourceIsNotFoundException(
	    	                "Purchase Order not found with id " + id));

	        order.setSupplierId(dto.getSupplierId());

	        return purchaseOrderRepository.save(order);
	    }

	    
	    public PurchaseOrder approvePurchaseOrder(Long id) {

	    	 PurchaseOrder order = purchaseOrderRepository.findById(id)
	    	            .orElseThrow(() ->
	    	                    new ResourceIsNotFoundException(
	    	                            "Purchase Order not found with id " + id));
	        if(order.getStatus()!=OrderStatus.PENDING) {
	        	 throw new InvalidOrderStatusException(
	        	            "Only PENDING orders can be approved");
	        }

	        order.setStatus(OrderStatus.APPROVED);

	        return purchaseOrderRepository.save(order);
	    }

	    
	    public PurchaseOrder rejectPurchaseOrder(Long id) {

	    	 PurchaseOrder order = purchaseOrderRepository.findById(id)
	    	            .orElseThrow(() ->
	    	                    new ResourceIsNotFoundException(
	    	                            "Purchase Order not found with id " + id));
	    	 if(order.getStatus()==OrderStatus.REJECTED) {
	        	 throw new InvalidOrderStatusException(
	        	            "Only approved or oedning orders can be rejected");
	        }

	        order.setStatus(OrderStatus.REJECTED);

	        return purchaseOrderRepository.save(order);
	    }
	    @Override
	    public Double getMonthlyPurchase(Integer month, Integer year) {
	        return purchaseOrderRepository.getMonthlyPurchase(month, year);
	    }

}
