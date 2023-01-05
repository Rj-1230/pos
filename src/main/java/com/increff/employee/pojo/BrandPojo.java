package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;
import org.omg.CORBA.INTERNAL;

import javax.persistence.*;

@Entity
@Table(name="brand")

@Getter
@Setter
public class BrandPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BrandID")
    private int id;
    @Column(name="Brand", nullable=false)
    private String brand;
    @Column(name="Category", nullable=false)
    private String category;

}
