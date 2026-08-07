package com.marketingtrade.controller;

import com.marketingtrade.dto.ProductTypeRequest;
import com.marketingtrade.dto.ProductTypeResponse;
import com.marketingtrade.service.ProductTypeService;
import java.util.stream.Collectors;
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
    public ResponseEntity<List<ProductTypeResponse>> findAll(
            @RequestParam(required = false) Long productId) {

        if (productId != null) {
            log.info("Fetching product types for productId: {}", productId);
            var list = service.findByProduct(productId);
            var responses = list.stream()
                    .map(pt -> new ProductTypeResponse(pt.getId(), pt.getProduct() != null ? pt.getProduct().getId() : null, pt.getProduct() != null ? pt.getProduct().getProductName() : null, pt.getTypeName(), pt.getSpecification(), pt.getUnit(), pt.isActive()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        }
        log.info("Fetching all product types");
        var all = service.findAll();
        var responses = all.stream()
                .map(pt -> new ProductTypeResponse(pt.getId(), pt.getProduct() != null ? pt.getProduct().getId() : null, pt.getProduct() != null ? pt.getProduct().getProductName() : null, pt.getTypeName(), pt.getSpecification(), pt.getUnit(), pt.isActive()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
        public ResponseEntity<ProductTypeResponse> findById(@PathVariable Long id) {
        log.info("Fetching product type with id: {}", id);
            var pt = service.findById(id);
            return ResponseEntity.ok(new ProductTypeResponse(pt.getId(), pt.getProduct() != null ? pt.getProduct().getId() : null, pt.getProduct() != null ? pt.getProduct().getProductName() : null, pt.getTypeName(), pt.getSpecification(), pt.getUnit(), pt.isActive()));
        }

    @PostMapping
        public ResponseEntity<ProductTypeResponse> create(
            @Valid @RequestBody ProductTypeRequest request) {
        try {
                var pt = service.create(request);
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(new ProductTypeResponse(pt.getId(), pt.getProduct() != null ? pt.getProduct().getId() : null, pt.getProduct() != null ? pt.getProduct().getProductName() : null, pt.getTypeName(), pt.getSpecification(), pt.getUnit(), pt.isActive()));
            } catch (Exception e) {
                log.error("Error occurred while creating product type", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

    @PutMapping("/{id}")
        public ResponseEntity<ProductTypeResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductTypeRequest request) {
        log.info("Updating product type with id: {}", id);
            var pt = service.update(id, request);
            return ResponseEntity.ok(new ProductTypeResponse(pt.getId(), pt.getProduct() != null ? pt.getProduct().getId() : null, pt.getProduct() != null ? pt.getProduct().getProductName() : null, pt.getTypeName(), pt.getSpecification(), pt.getUnit(), pt.isActive()));
        }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.info("Deactivating product type with id: {}", id);
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}