package com.api.pa.services;

import com.api.pa.models.Product;
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
    public Product save(Product product) {
        return productRepository.save(product);
    }
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    public Optional<Product> findById(Integer productId) {
        return productRepository.findById(productId);
    }
    @Transactional
    public void delete(Product product) {
        productRepository.delete(product);
    }
}
