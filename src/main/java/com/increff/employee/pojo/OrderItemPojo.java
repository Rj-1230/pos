package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="order_Items", uniqueConstraints={@UniqueConstraint(columnNames={"MyOrderID", "ProductID"})})

@Getter
@Setter

public class OrderItemPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="orderItem_generator", sequenceName = "orderItem_seq", allocationSize=1,initialValue = 100001)
    @Column(name="OrderItemID", nullable=false)
    private int orderItemId;
    @Column(name="MyOrderID", nullable=false)
    private int orderId;
    @Column(name="ProductID", nullable=false)
    private int productId;
    @Column(name="ProductQuantity", nullable=false)
    private int quantity;
    @Column(name="SellingPrice", nullable=false)
    private Double sellingPrice ;


}
