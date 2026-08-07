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
                return ResponseEntity.ok(service.findByProductDto(productId));
            }
            log.info("Fetching all product types");
            return ResponseEntity.ok(service.findAllDto());
    }

    @GetMapping("/{id}")
        public ResponseEntity<ProductTypeResponse> findById(@PathVariable Long id) {
            log.info("Fetching product type with id: {}", id);
            return ResponseEntity.ok(service.findByIdDto(id));
        }

    @PostMapping
        public ResponseEntity<ProductTypeResponse> create(
            @Valid @RequestBody ProductTypeRequest request) {
        try {
                var pt = service.createDto(request);
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(pt);
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
            var pt = service.updateDto(id, request);
            return ResponseEntity.ok(pt);
        }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.info("Deactivating product type with id: {}", id);
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}