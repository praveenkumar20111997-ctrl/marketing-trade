package com.marketingtrade.controller;

import com.marketingtrade.entity.SellingPrice;
import com.marketingtrade.service.SellingPriceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/selling-prices")
@CrossOrigin(origins = "http://localhost:5173")
public class SellingPriceController {

    private final SellingPriceService service;

    public SellingPriceController(
            SellingPriceService service) {
        this.service = service;
    }

    // ============================================================
    // GET ALL
    // ============================================================

    @GetMapping
    public ResponseEntity<List<SellingPrice>> getAll() {

        return ResponseEntity.ok(
                service.getAll()
        );
    }

    // ============================================================
    // GET BY ID
    // ============================================================

    @GetMapping("/{id}")
    public ResponseEntity<SellingPrice> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getById(id)
        );
    }

    // ============================================================
    // GET BY SHOP
    // ============================================================

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<List<SellingPrice>> getByShop(
            @PathVariable Long shopId) {

        return ResponseEntity.ok(
                service.getByShop(shopId)
        );
    }

    // ============================================================
    // GET BY PRODUCT TYPE
    // ============================================================

    @GetMapping("/product-type/{productTypeId}")
    public ResponseEntity<List<SellingPrice>> getByProductType(
            @PathVariable Long productTypeId) {

        return ResponseEntity.ok(
                service.getByProductType(productTypeId)
        );
    }

    // ============================================================
    // GET BY SHOP + PRODUCT TYPE
    // ============================================================

    @GetMapping("/shop/{shopId}/product-type/{productTypeId}")
    public ResponseEntity<List<SellingPrice>>
    getByShopAndProductType(
            @PathVariable Long shopId,
            @PathVariable Long productTypeId) {

        return ResponseEntity.ok(
                service.getByShopAndProductType(
                        shopId,
                        productTypeId
                )
        );
    }

    // ============================================================
    // FIND APPLICABLE SELLING PRICE
    // ============================================================

    @GetMapping("/applicable")
    public ResponseEntity<BigDecimal> getApplicablePrice(

            @RequestParam Long productTypeId,

            @RequestParam Long shopId,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return ResponseEntity.ok(
                service.findApplicablePrice(
                        productTypeId,
                        shopId,
                        date
                )
        );
    }

    // ============================================================
    // CREATE
    // ============================================================

    @PostMapping
    public ResponseEntity<SellingPrice> create(

            @RequestParam Long shopId,

            @RequestParam Long productTypeId,

            @RequestParam BigDecimal sellingPrice,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate effectiveFrom,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate effectiveTo) {

        SellingPrice result =
                service.create(
                        shopId,
                        productTypeId,
                        sellingPrice,
                        effectiveFrom,
                        effectiveTo
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @PutMapping("/{id}")
    public ResponseEntity<SellingPrice> update(

            @PathVariable Long id,

            @RequestParam(required = false)
            Long shopId,

            @RequestParam(required = false)
            Long productTypeId,

            @RequestParam(required = false)
            BigDecimal sellingPrice,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate effectiveFrom,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate effectiveTo,

            @RequestParam(required = false)
            Boolean active) {

        SellingPrice result =
                service.update(
                        id,
                        shopId,
                        productTypeId,
                        sellingPrice,
                        effectiveFrom,
                        effectiveTo,
                        active
                );

        return ResponseEntity.ok(result);
    }

    // ============================================================
    // DELETE
    // ============================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    // ============================================================
    // DEACTIVATE
    // ============================================================

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<SellingPrice> deactivate(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.deactivate(id)
        );
    }
}