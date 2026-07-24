package com.smart_warehouse_management.orders.serviceimpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smart_warehouse_management.orders.dto.SalesItemDTO;
import com.smart_warehouse_management.orders.dto.SalesOrderRequestDTO;
import com.smart_warehouse_management.orders.entity.SalesItem;
import com.smart_warehouse_management.orders.entity.SalesOrder;
import com.smart_warehouse_management.orders.enums.OrderStatus;
import com.smart_warehouse_management.orders.exception.ResourceIsNotFoundException;
import com.smart_warehouse_management.orders.repository.SalesOrderRepository;
import com.smart_warehouse_management.orders.service.*;

@Service
public class SalesOrderServiceImpl implements SalesOrderService{
	
	 private final SalesOrderRepository salesOrderRepository;

	    public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository) {
	        this.salesOrderRepository = salesOrderRepository;
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

	    
	    public SalesOrder dispatchSalesOrder(Long id) {

	        SalesOrder order = salesOrderRepository.findById(id).orElseThrow();

	        order.setStatus(OrderStatus.DISPATCHED);

	        return salesOrderRepository.save(order);
	    }

	    
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
