package com.marketingtrade.controller;

import com.marketingtrade.dto.ProductRequest;
import com.marketingtrade.dto.ProductResponse;
import com.marketingtrade.service.ProductService;
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
                return ResponseEntity.ok(service.findAll());
            } catch (Exception e) {
                log.error("Error fetching products: ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

    @GetMapping("/{id}")
        public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
            try {
                return ResponseEntity.ok(service.findById(id));
            } catch (RuntimeException e) {
                log.info("Product not found with id: {}", id);
                return ResponseEntity.notFound().build();
            }
        }

    @PostMapping
        public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody ProductRequest request) {
        try {
                var productResp = service.create(request);
                log.info("Product created");
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                        .body(productResp);
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
                var productResp = service.update(id, request);
                return ResponseEntity.ok(productResp);
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
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