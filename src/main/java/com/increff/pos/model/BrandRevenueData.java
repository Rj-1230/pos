package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRevenueData {
    private String brand;
    private int quantity;
    private double totalBrandRevenue;
}