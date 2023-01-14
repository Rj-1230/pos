package com.increff.pos.model;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderData  extends OrderForm{
   private int orderId;
   private String orderCreateTime;
    private String orderPlaceTime;
    private String status;
}
