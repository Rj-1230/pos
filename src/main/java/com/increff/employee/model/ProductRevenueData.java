package com.increff.employee.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRevenueData {
    private int productId;
    private String barcode;
    private String name;
    private String brand;
    private String category;
    private double mrp;
    private int quantity;
    private double total;

}