package com.marketingtrade.controller;

import com.marketingtrade.dto.ProductRequest;
import com.marketingtrade.dto.ProductResponse;
import com.marketingtrade.entity.Product;
import com.marketingtrade.service.ProductService;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
        public ResponseEntity<List<ProductResponse>> findAll() {
        log.info("Fetching all products");
        try {
            List<Product> products = service.findAll();
            log.info("Found {} products", products.size());
                List<ProductResponse> responses = products.stream()
                        .map(p -> new ProductResponse(p.getId(), p.getProductName(), p.getBrand(), p.isActive()))
                        .collect(Collectors.toList());
                return ResponseEntity.ok(responses);
            }   catch (Exception e) {
                log.error("Error fetching products: ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

    @GetMapping("/{id}")
        public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        Product product = service.findById(id);
        if (product == null) {
            log.info("Product not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Product found with id: {}", id);
            return ResponseEntity.ok(new ProductResponse(product.getId(), product.getProductName(), product.getBrand(), product.isActive()));
    }

    @PostMapping
        public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody ProductRequest request) {
        try {
            Product product = service.create(request);
            log.info("Product created with id: {}", product.getId());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                        .body(new ProductResponse(product.getId(), product.getProductName(), product.getBrand(), product.isActive()));
        } catch (Exception e) {
            log.error("Error creating product: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
        public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        try {
            Product product = service.update(id, request);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }
            log.info("Product updated with id: {}", product.getId());
                return ResponseEntity.ok(new ProductResponse(product.getId(), product.getProductName(), product.getBrand(), product.isActive()));
        } catch (Exception e) {
            log.error("Error updating product: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.info("Deactivating product with id: {}", id);
        service.deactivate(id);
        log.info("Product deactivated with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}