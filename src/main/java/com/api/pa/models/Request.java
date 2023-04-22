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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private LocalDateTime dateIn;
    @Column
    private LocalDateTime dateOut;
}
