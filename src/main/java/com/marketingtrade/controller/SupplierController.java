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
            var suppliers = service.getAll();
            var responses = suppliers.stream()
                    .map(s -> new SupplierResponse(s.getId(), s.getSupplierName(), s.getContact(), s.getLocation(), s.getAddress(), s.getActive()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        }

    @GetMapping("/{id}")
        public ResponseEntity<SupplierResponse> getById(@PathVariable Long id) {
            var s = service.getById(id);
            return ResponseEntity.ok(new SupplierResponse(s.getId(), s.getSupplierName(), s.getContact(), s.getLocation(), s.getAddress(), s.getActive()));
        }

    @PostMapping
        public ResponseEntity<SupplierResponse> create(
            @Valid @RequestBody SupplierRequest request) {
        log.info("Creating new supplier");
            var s = service.create(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new SupplierResponse(s.getId(), s.getSupplierName(), s.getContact(), s.getLocation(), s.getAddress(), s.getActive()));
        }

    @PutMapping("/{id}")
        public ResponseEntity<SupplierResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SupplierRequest request) {
        log.info("Updating supplier with id: {}", id);
            var s = service.update(id, request);
            return ResponseEntity.ok(new SupplierResponse(s.getId(), s.getSupplierName(), s.getContact(), s.getLocation(), s.getAddress(), s.getActive()));
        }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.info("Deactivating supplier with id: {}", id);
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}