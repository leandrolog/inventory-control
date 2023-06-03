package com.api.pa.dtos;

import com.api.pa.models.RequestStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewRequestDto {


    private Integer requestId;
    private RequestStatus status;

}
