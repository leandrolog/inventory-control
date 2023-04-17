package com.api.pa.controllers;


import com.api.pa.dtos.RequestDto;
import com.api.pa.models.RequestModel;
import com.api.pa.services.RequestService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;


@Controller
public class RequestController {


    final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("new-request")
    public ResponseEntity<Object> saveRequest(@RequestBody @Valid RequestDto requestDto) {
        var requestModel = new RequestModel();
        BeanUtils.copyProperties(requestDto, requestModel);
        requestModel.setDateIn(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.OK).body(requestService.save(requestModel));
    }
    @GetMapping("requests")
    public ResponseEntity<Page> getAllRequests (@PageableDefault(page = 0, size = 10, sort = "requestId", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(requestService.findAll(pageable));
    }
    @GetMapping("request/{requestId}")
    public ResponseEntity<Object> getRequest (@PathVariable("requestId") Integer requestId){
        return ResponseEntity.status(HttpStatus.OK).body(requestService.findById(requestId));
    }
    @DeleteMapping("request/{requestId}")
    public ResponseEntity<Object> deleteRequest (@PathVariable("requestId") Integer requestId){
        Optional<RequestModel> requestModelOptional = requestService.findById(requestId);
        requestService.delete(requestModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Request deletado");
    }///

}