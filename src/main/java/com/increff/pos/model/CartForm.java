package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartForm {
    private String barcode;
    private int quantity;
    private double sellingPrice;

}
