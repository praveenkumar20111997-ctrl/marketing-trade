package com.marketingtrade.controller;

import com.marketingtrade.dto.DeliveryRequest;
import com.marketingtrade.dto.DeliveryResponse;
import com.marketingtrade.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService service;

    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DeliveryResponse>> findAll() {
        log.info("Fetching all deliveries");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponse> findById(
            @PathVariable Long id) {
        log.info("Fetching delivery with id: {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DeliveryResponse> create(
            @Valid @RequestBody DeliveryRequest request) {
        log.info("Creating new delivery");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }
}