package com.api.pa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {

    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    @Id
    private Integer productId;
    @Column(nullable = false, length = 30)
    private String productName;
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Request> request;
    @Column(length = 30)
    private Date expirationDate;
    @Column(nullable = false, length = 31)
    private Integer quantity;
    @Column(nullable = false)
    private Double unitPrice;
    @Column(length = 50)
    private String supplier;
}
