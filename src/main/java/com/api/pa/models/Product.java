package com.api.pa.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer productId;
    @Column(nullable = false, length = 30)
    private String productName;
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private Request request;
    @Column(length = 30)
    private Date expirationDate;
    @Column(nullable = false, length = 31)
    private Integer quantity;
    @Column(nullable = false)
    private Double unitPrice;
    @Column(nullable = false, length = 50)
    private String supplier;
}
