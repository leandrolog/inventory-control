package com.api.pa.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDto {

    @NotBlank
    private String productName;
    private LocalDateTime expirationDate;
    @NotNull
    private Integer quantity;
    private Double unitPrice;
    private String supplier;

}
