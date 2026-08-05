package com.marketingtrade.controller;

import com.marketingtrade.dto.DeliveryRequest;
import com.marketingtrade.dto.DeliveryResponse;
import com.marketingtrade.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService service;

    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    @GetMapping
    public List<DeliveryResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public DeliveryResponse findById(
            @PathVariable Long id) {

        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<DeliveryResponse> create(
            @Valid @RequestBody DeliveryRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }
}