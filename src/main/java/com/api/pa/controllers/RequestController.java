package com.api.pa.controllers;

import com.api.pa.dtos.RequestDto;
import com.api.pa.dtos.ViewRequestDto;
import com.api.pa.models.Product;
import com.api.pa.models.Request;
import com.api.pa.models.RequestStatus;
import com.api.pa.models.User;
import com.api.pa.repositories.ProductRepository;
import com.api.pa.repositories.RequestRepository;
import com.api.pa.services.RequestService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private ProductRepository productRepository;
    final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/new-request")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Object> saveRequest(@RequestBody @Valid RequestDto requestDto) {
        var requestModel = new Request();
        BeanUtils.copyProperties(requestDto, requestModel);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        requestModel.setStatus(RequestStatus.PENDING);
        requestModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        requestModel.setUser(user);
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        requestModel.setProduct(product);

        var responseEntity = requestService.save(requestModel);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    @PostMapping("/request-status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> acceptRequest(@RequestBody @Valid ViewRequestDto viewRequestDto) {
        Optional<Request> requestOptional = requestRepository.findById(viewRequestDto.getRequestId());
        if (!requestOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request não encontrada!");
        }
        RequestStatus newStatus = viewRequestDto.getStatus();
        Request request = requestOptional.get();

        switch (newStatus) {
            case ACCEPTED -> {
                Product product = request.getProduct();
                int newQuantity = product.getQuantity() - request.getQuantity();
                if (newQuantity < 0) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quantidade solicitada não disponivel!!!");
                }
                request.setStatus(newStatus);
                request.setAcceptedAt(LocalDateTime.now());
                product.setQuantity(newQuantity);
                productRepository.save(product);
                return ResponseEntity.status(HttpStatus.OK).body("Solicitação aceita!");
            }
            case RETURNED -> {
                request.setStatus(newStatus);
                request.setReturnedAt(LocalDateTime.now());
                Product product = request.getProduct();
                int originalQuantity = product.getQuantity() + request.getQuantity();
                product.setQuantity(originalQuantity);
                productRepository.save(product);
                return ResponseEntity.status(HttpStatus.OK).body("Devolução feita!");
            }
            case CANCELED -> {
                request.setStatus(newStatus);
                request.setCanceledAt(LocalDateTime.now());
                requestRepository.save(request);
                return ResponseEntity.status(HttpStatus.OK).body("Solicitação cancelada!");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(requestRepository.save(request));
    }
    @GetMapping("/requests")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Page> getAllRequests(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = user.getUserId();

        var role = user.getAuthorities().stream().toList().get(0).getAuthority();
        if ("ROLE_ADMIN".equals(role)) {
            return ResponseEntity.status(HttpStatus.OK).body(requestRepository.findAllRequests(pageable));
        }
        var findUserRequest = requestRepository.findByUserUserId(id, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(findUserRequest);
    }

    @GetMapping("request/{requestId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getRequest(@PathVariable("requestId") Integer requestId) {
        Optional<Request> requestOptional = requestService.findById(requestId);
        if (!requestOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(requestOptional.get());
    }

    @DeleteMapping("request/{requestId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteRequest(@PathVariable("requestId") Integer requestId) {

        Optional<Request> requestModelOptional = requestService.findById(requestId);
        if (!requestModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found.");
        }
        requestService.delete(requestModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Request deletado");
    }///

}
