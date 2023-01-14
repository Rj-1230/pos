package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemData {
    private int orderItemId;
    private String productName;
    private double sellingPrice;
    private int orderId;
    private int productId;

    private int quantity;


}
