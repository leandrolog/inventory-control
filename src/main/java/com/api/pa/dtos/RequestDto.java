package com.api.pa.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RequestDto {

    @NotBlank
    private String requester;
    private Date dateIn;
    private Date dateOut;
    private Integer productId;

}
