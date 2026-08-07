package com.marketingtrade.service;

import com.marketingtrade.dto.ProductRequest;
import com.marketingtrade.dto.ProductResponse;
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

    // Entity-returning methods (for internal use)
    public java.util.List<Product> findAllEntities() {
        return repository.findAll();
    }

    public Product findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found: " + id));
    }

    @Transactional
    public Product createEntity(ProductRequest request) {

        Product product = new Product();
        log.info("Creating new product with name: {}", request.getProductName());
        product.setProductName(request.getProductName());
        product.setBrand(request.getBrand());
        product.setActive(true);
        log.info("Creating new product: {}", product.getProductName());
        return repository.save(product);
    }

    @Transactional
    public Product updateEntity(Long id, ProductRequest request) {

        Product product = findEntityById(id);

        product.setProductName(request.getProductName());
        product.setBrand(request.getBrand());
        log.info("Updating product with ID: {}", product.getId());
        return repository.save(product);
    }

    @Transactional
    public void deactivate(Long id) {

        Product product = findEntityById(id);
        log.info("Deactivating product with ID: {}", product.getId());
        product.setActive(false);

        repository.save(product);
    }

    // -----------------------------
    // DTO returning methods for controllers
    // -----------------------------

    public java.util.List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(EntityDtoMapper::toProductResponse)
                .toList();
    }

    public ProductResponse findById(Long id) {
        Product product = findEntityById(id);
        return EntityDtoMapper.toProductResponse(product);
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product product = createEntity(request);
        return EntityDtoMapper.toProductResponse(product);
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = updateEntity(id, request);
        return EntityDtoMapper.toProductResponse(product);
    }
}


