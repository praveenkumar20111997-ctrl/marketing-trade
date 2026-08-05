package com.marketingtrade.service;

import com.marketingtrade.dto.ProductRequest;
import com.marketingtrade.entity.Product;
import com.marketingtrade.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found: " + id));
    }

    @Transactional
    public Product create(ProductRequest request) {

        Product product = new Product();

        product.setProductName(request.productName());
        product.setBrand(request.brand());
        product.setActive(true);

        return repository.save(product);
    }

    @Transactional
    public Product update(Long id, ProductRequest request) {

        Product product = findById(id);

        product.setProductName(request.productName());
        product.setBrand(request.brand());

        return repository.save(product);
    }

    @Transactional
    public void deactivate(Long id) {

        Product product = findById(id);

        product.setActive(false);

        repository.save(product);
    }
}