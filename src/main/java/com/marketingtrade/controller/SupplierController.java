package com.marketingtrade.controller;

import com.marketingtrade.dto.SupplierRequest;
import com.marketingtrade.entity.Supplier;
import com.marketingtrade.service.SupplierService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }

    @GetMapping
    public List<Supplier> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Supplier getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<Supplier> create(
            @Valid @RequestBody SupplierRequest request) {
        log.info("Creating new supplier");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> update(
            @PathVariable Long id,
            @Valid @RequestBody SupplierRequest request) {
        log.info("Updating supplier with id: {}", id);
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.info("Deactivating supplier with id: {}", id);
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}