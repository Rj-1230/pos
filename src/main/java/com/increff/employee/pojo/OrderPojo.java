package com.increff.employee.pojo;

import javax.persistence.*;
//import java.time.String;

@Entity
@Table(name="orders")
//Order is a reserved keyword so use diff table name
public class OrderPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_generator")
    @SequenceGenerator(name="order_generator", sequenceName = "order_seq", allocationSize=1,initialValue = 101)
    @Column(name="MyOrderID", nullable=false, length=512)
    private int orderId;

    @Column(name="CustomerName", nullable=false, length=512)
    private String customerName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    private String orderTime;
//    hei
    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

}
