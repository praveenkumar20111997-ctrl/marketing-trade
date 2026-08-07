package com.marketingtrade.controller;

import com.marketingtrade.dto.ProductTypeRequest;
import com.marketingtrade.entity.ProductType;
import com.marketingtrade.service.ProductTypeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/product-types")
public class ProductTypeController {

    private final ProductTypeService service;

    public ProductTypeController(ProductTypeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductType>> findAll(
            @RequestParam(required = false) Long productId) {

        if (productId != null) {
            log.info("Fetching product types for productId: {}", productId);
            return ResponseEntity.ok(service.findByProduct(productId));
        }
        log.info("Fetching all product types");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductType> findById(@PathVariable Long id) {
        log.info("Fetching product type with id: {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductType> create(
            @Valid @RequestBody ProductTypeRequest request) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(service.create(request));
        } catch (Exception e) {
            log.error("Error occurred while creating product type", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductType> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductTypeRequest request) {
        log.info("Updating product type with id: {}", id);
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.info("Deactivating product type with id: {}", id);
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}