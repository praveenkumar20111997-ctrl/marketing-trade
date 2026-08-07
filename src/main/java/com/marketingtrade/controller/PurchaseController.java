package com.marketingtrade.controller;

import com.marketingtrade.dto.PurchaseRequest;
import com.marketingtrade.dto.PurchaseResponse;
import com.marketingtrade.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService service;

    public PurchaseController(PurchaseService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> findAll(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to) {

        if (from != null && to != null) {
            log.info("Fetching purchases from {} to {}", from, to);
            return ResponseEntity.ok(service.findByDate(from, to));
        }
        log.info("Fetching all purchases");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponse> findById(
            @PathVariable Long id) {

        log.info("Fetching purchase with id: {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PurchaseResponse> create(
            @Valid @RequestBody PurchaseRequest request) {
        log.info("Creating new purchase");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }
}