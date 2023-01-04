package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="inventory")

@Getter
@Setter

public class InventoryPojo {
    private int quantity;
    @Id
    private int productId;

}
