package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartData {
    private int cartItemId;
    private int productId;
    private String productName;
    private double sellingPrice;
    private int counterId;
    private int quantity;

}
