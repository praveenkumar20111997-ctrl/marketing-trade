package com.marketingtrade.service;

import com.marketingtrade.dto.ProductRequest;
import com.marketingtrade.entity.Product;
import com.marketingtrade.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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
        log.info("Creating new product with name: {}", request.productName());
        product.setProductName(request.productName());
        product.setBrand(request.brand());
        product.setActive(true);
        log.info("Creating new product: {}", product.getProductName());
        return repository.save(product);
    }

    @Transactional
    public Product update(Long id, ProductRequest request) {

        Product product = findById(id);

        product.setProductName(request.productName());
        product.setBrand(request.brand());
        log.info("Updating product with ID: {}", product.getId());
        return repository.save(product);
    }

    @Transactional
    public void deactivate(Long id) {

        Product product = findById(id);
        log.info("Deactivating product with ID: {}", product.getId());
        product.setActive(false);

        repository.save(product);
    }
}