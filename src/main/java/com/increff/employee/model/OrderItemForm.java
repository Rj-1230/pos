package com.increff.employee.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemForm {
    private int orderId;
    private String barcode;
    private int quantity;

}
