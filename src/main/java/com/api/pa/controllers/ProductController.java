package com.api.pa.controllers;


import com.api.pa.dtos.ProductDto;
import com.api.pa.models.ProductModel;
import com.api.pa.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {


    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/new-product")
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductDto productDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<Object> getAllProducts(@PageableDefault(page = 0, size = 10, sort = "productId", direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll(pageable));
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<Object> getProduct(@PathVariable(value = "productId") Integer productId){
        Optional<ProductModel> productModelOptional = productService.findById(productId);
        if(!productModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
    }
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "productId") Integer productId){
        Optional<ProductModel> productModelOptional = productService.findById(productId);
        if(!productModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        productService.delete(productModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso");
    }
    @PutMapping("/product/{productId}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "productId") Integer productId,
                                              @RequestBody @Valid ProductDto productDto){
        Optional<ProductModel> productModelOptional = productService.findById(productId);
        if(!productModelOptional.isPresent()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productDto, productModel);
        productModel.setProductId(productModelOptional.get().getProductId());
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productModel));
    }

}
