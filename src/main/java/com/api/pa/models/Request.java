package com.api.pa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "requests")
public class Request {

    @SequenceGenerator(
            name = "request_sequence",
            sequenceName = "request_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "request_sequence"
    )
    @Id
    private Integer request_id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private LocalDateTime createdAt;
    @Column
    private String reason;
    @Column
    private LocalDateTime acceptedAt;
    @Column
    private LocalDateTime returnedAt;
    @Column
    private LocalDateTime canceledAt;
    @Column(columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
