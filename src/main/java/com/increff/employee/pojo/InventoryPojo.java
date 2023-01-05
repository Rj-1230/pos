package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="inventory")
//        , uniqueConstraints={@UniqueConstraint(columnNames={"ProductId"})})

@Getter
@Setter

public class InventoryPojo {
    @Column(name="ProductQuantity", nullable=false)
    private int quantity;
    @Id
    @Column(name="ProductId", nullable=false)
    private int productId;

}
