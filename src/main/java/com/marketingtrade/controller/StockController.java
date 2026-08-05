package com.marketingtrade.controller;

import com.marketingtrade.dto.StockAdjustmentRequest;
import com.marketingtrade.dto.StockResponse;
import com.marketingtrade.entity.InventoryTransaction;
import com.marketingtrade.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @GetMapping
    public List<StockResponse> getAllStock() {
        return service.getAllStock();
    }

    @GetMapping("/{productTypeId}")
    public StockResponse getStock(
            @PathVariable Long productTypeId) {

        return service.getStock(productTypeId);
    }

    @GetMapping("/{productTypeId}/history")
    public List<InventoryTransaction> history(
            @PathVariable Long productTypeId) {

        return service.history(productTypeId);
    }

    @PostMapping("/{productTypeId}/adjust")
    public ResponseEntity<InventoryTransaction> adjust(
            @PathVariable Long productTypeId,
            @Valid @RequestBody StockAdjustmentRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.adjust(
                        productTypeId,
                        request.quantity(),
                        request.notes()
                ));
    }
}