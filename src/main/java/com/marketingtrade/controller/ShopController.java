package com.marketingtrade.controller;

import com.marketingtrade.dto.ShopRequest;
import com.marketingtrade.dto.ShopResponse;
import com.marketingtrade.service.ShopService;
import java.util.stream.Collectors;
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
        public ResponseEntity<List<ShopResponse>> getAll() {
            log.info("Fetching all shops");
            return ResponseEntity.ok(service.getAllDto());
        }

    @GetMapping("/{id}")
        public ResponseEntity<ShopResponse> getById(@PathVariable Long id) {
            log.info("Fetching shop with id: {}", id);
            return ResponseEntity.ok(service.getByIdDto(id));
        }

    @PostMapping
        public ResponseEntity<ShopResponse> create(
            @Valid @RequestBody ShopRequest request) {

        log.info("Creating new shop");
            var shop = service.createDto(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(shop);
        }

    @PutMapping("/{id}")
        public ResponseEntity<ShopResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ShopRequest request) {

        log.info("Updating shop with id: {}", id);
            var shop = service.updateDto(id, request);
            return ResponseEntity.ok(shop);
        }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.info("Deactivating shop with id: {}", id);
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}