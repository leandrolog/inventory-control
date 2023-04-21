package com.api.pa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "requests")
public class Request {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer requestId;
    @OneToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;
    @Column(nullable = false)
    private String requester;
    @Column(nullable = false)
    private LocalDateTime dateIn;
    @Column
    private LocalDateTime dateOut;
}
