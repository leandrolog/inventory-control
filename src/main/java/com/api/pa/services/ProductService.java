package com.api.pa.services;

import com.api.pa.models.ProductModel;
import com.api.pa.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {
    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductModel save(ProductModel productModel) {
        return productRepository.save(productModel);
    }
    public Page<ProductModel> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    public Optional<ProductModel> findById(Integer productId) {
        return productRepository.findById(productId);
    }
    @Transactional
    public void delete(ProductModel productModel) {
        productRepository.delete(productModel);
    }
}
