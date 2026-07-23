package com.smart_warehouse_management.orders.entity;



import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="sales_items")
@Data
public class SalesItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private SalesOrder salesOrder;

    private Long productId;

    private Integer quantity;

    private Double price;

    private Double subtotal;

}