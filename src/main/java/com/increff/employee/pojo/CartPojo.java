package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="cart_Items")

@Getter
@Setter

public class CartPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CartItemID", nullable=false)
    private int cartItemId;
    @Column(name="ProductName", nullable=false)
    private String productName;
    @Column(name="ProductQuantity", nullable=false)
    private int quantity;
    @Column(name="SellingPrice", nullable=false)
    private Double sellingPrice ;

    @Column(name="CounterID", nullable=false)
    private int counterId;


}
