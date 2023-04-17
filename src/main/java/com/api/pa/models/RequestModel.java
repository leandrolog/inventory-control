package com.api.pa.models;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "requests")
public class RequestModel {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Integer requestId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private ProductModel product;
    @Column(nullable = false)
    private String requester;
    @Column(nullable = false)
    private LocalDateTime dateIn;
    @Column
    private LocalDateTime dateOut;

}
