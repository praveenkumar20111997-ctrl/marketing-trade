package com.marketingtrade.controller;

import com.marketingtrade.dto.ShopRequest;
import com.marketingtrade.entity.Shop;
import com.marketingtrade.service.ShopService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopService service;

    public ShopController(ShopService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Shop>> getAll() {
        log.info("Fetching all shops");
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shop> getById(@PathVariable Long id) {
        log.info("Fetching shop with id: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<Shop> create(
            @Valid @RequestBody ShopRequest request) {

        log.info("Creating new shop");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shop> update(
            @PathVariable Long id,
            @Valid @RequestBody ShopRequest request) {

        log.info("Updating shop with id: {}", id);
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.info("Deactivating shop with id: {}", id);
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}