package com.marketingtrade.controller;

import com.marketingtrade.dto.SupplierRequest;
import com.marketingtrade.dto.SupplierResponse;
import com.marketingtrade.service.SupplierService;
import java.util.stream.Collectors;
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
        public ResponseEntity<List<SupplierResponse>> getAll() {
            return ResponseEntity.ok(service.getAllDto());
        }

    @GetMapping("/{id}")
        public ResponseEntity<SupplierResponse> getById(@PathVariable Long id) {
            return ResponseEntity.ok(service.getByIdDto(id));
        }

    @PostMapping
        public ResponseEntity<SupplierResponse> create(
            @Valid @RequestBody SupplierRequest request) {
        log.info("Creating new supplier");
            var s = service.createDto(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(s);
        }

    @PutMapping("/{id}")
        public ResponseEntity<SupplierResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SupplierRequest request) {
        log.info("Updating supplier with id: {}", id);
            var s = service.updateDto(id, request);
            return ResponseEntity.ok(s);
        }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.info("Deactivating supplier with id: {}", id);
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}