package com.api.pa.dtos;

import com.api.pa.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RequestDto {

    private Integer userId;
    private Date dateIn;
    private Date dateOut;
    private Integer productId;

}
