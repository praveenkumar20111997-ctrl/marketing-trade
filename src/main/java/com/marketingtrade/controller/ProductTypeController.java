package com.marketingtrade.controller;

import com.marketingtrade.dto.ProductTypeRequest;
import com.marketingtrade.entity.ProductType;
import com.marketingtrade.service.ProductTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-types")
public class ProductTypeController {

    private final ProductTypeService service;

    public ProductTypeController(ProductTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductType> findAll(
            @RequestParam(required = false) Long productId) {

        if (productId != null) {
            return service.findByProduct(productId);
        }

        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProductType findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProductType> create(
            @Valid @RequestBody ProductTypeRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @PutMapping("/{id}")
    public ProductType update(
            @PathVariable Long id,
            @Valid @RequestBody ProductTypeRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long id) {
        service.deactivate(id);
    }
}