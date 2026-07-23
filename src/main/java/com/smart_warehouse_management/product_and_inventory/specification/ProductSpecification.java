package com.smart_warehouse_management.Product_And_Inventory.specification;

import org.springframework.data.jpa.domain.Specification;

import com.smart_warehouse_management.Product_And_Inventory.entity.Product;

public class ProductSpecification {

    public static Specification<Product> hasProductName(String productName){

        return (root, query, cb) ->

                productName == null
                        ? null
                        : cb.like(
                                cb.lower(root.get("productName")),
                                "%" + productName.toLowerCase() + "%");
    }

    public static Specification<Product> hasCategory(String category){

        return (root, query, cb) ->

                category == null
                        ? null
                        : cb.equal(
                                root.get("category").get("categoryName"),
                                category);
    }

    public static Specification<Product> hasSupplier(String supplier){

        return (root, query, cb) ->

                supplier == null
                        ? null
                        : cb.equal(
                                root.get("supplier").get("companyName"),
                                supplier);
    }

    public static Specification<Product> minPrice(Double minPrice){

        return (root, query, cb) ->

                minPrice == null
                        ? null
                        : cb.greaterThanOrEqualTo(
                                root.get("price"),
                                minPrice);
    }

    public static Specification<Product> maxPrice(Double maxPrice){

        return (root, query, cb) ->

                maxPrice == null
                        ? null
                        : cb.lessThanOrEqualTo(
                                root.get("price"),
                                maxPrice);
    }

}