package com.smart_warehouse_management.orders.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "purchase_items")
@Data
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private PurchaseOrder purchaseOrder;

    @Column(name = "product_id")
    private Long productId;

    private Integer quantity;

    private Double price;
}