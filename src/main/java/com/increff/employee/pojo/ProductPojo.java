package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="product", uniqueConstraints={@UniqueConstraint(columnNames={"Barcode", "BrandID","ProductName"})})

@Getter
@Setter
public class ProductPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MyProductID", nullable=false)
    private int productId;
    @Column(name="Barcode", nullable=false)
    private String barcode;
    @Column(name="BrandID", nullable=false)
    private int brandId;
    @Column(name="ProductName", nullable=false)
    private String name;
    @Column(name="Mrp", nullable=false)
    private Double mrp;


}
