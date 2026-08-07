package com.marketingtrade.controller;

import com.marketingtrade.dto.StockAdjustmentRequest;
import com.marketingtrade.dto.StockResponse;
import com.marketingtrade.entity.InventoryTransaction;
import com.marketingtrade.service.StockService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<StockResponse>> getAllStock() {
        log.info("Fetching all stock information");
        return ResponseEntity.ok(service.getAllStock());
    }

    @GetMapping("/{productTypeId}")
    public ResponseEntity<StockResponse> getStock(
            @PathVariable Long productTypeId) {
        log.info("Fetching stock for productTypeId: {}", productTypeId);
        return ResponseEntity.ok(service.getStock(productTypeId));
    }

    @GetMapping("/{productTypeId}/history")
    public ResponseEntity<List<InventoryTransaction>> history(
            @PathVariable Long productTypeId) {
        log.info("Fetching stock history for productTypeId: {}", productTypeId);
        return ResponseEntity.ok(service.history(productTypeId));
    }

    @PostMapping("/{productTypeId}/adjust")
    public ResponseEntity<InventoryTransaction> adjust(
            @PathVariable Long productTypeId,
            @Valid @RequestBody StockAdjustmentRequest request) {
        log.info("Adjusting stock for productTypeId: {}, quantity: {}, notes: {}",
                productTypeId, request.quantity(), request.notes());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.adjust(
                        productTypeId,
                        request.quantity(),
                        request.notes()
                ));
    }
}