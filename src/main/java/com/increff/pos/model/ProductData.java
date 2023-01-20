package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductData {
    private int productId;

    private int brandId;
    private String barcode;
    private String name;
    private double mrp;

}
