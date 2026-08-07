package com.marketingtrade.controller;

import com.marketingtrade.dto.SellingPriceResponse;
import com.marketingtrade.entity.SellingPrice;
import com.marketingtrade.service.SellingPriceService;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
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
        public ResponseEntity<List<SellingPriceResponse>> getAll() {
        log.info("Fetching all selling prices");
            var list = service.getAll();
            var responses = list.stream()
                    .map(sp -> new SellingPriceResponse(
                            sp.getId(),
                            sp.getProductType() != null && sp.getProductType().getProduct() != null ? sp.getProductType().getProduct().getId() : null,
                            sp.getProductType() != null && sp.getProductType().getProduct() != null ? sp.getProductType().getProduct().getProductName() : null,
                            sp.getProductType() != null && sp.getProductType().getProduct() != null ? sp.getProductType().getProduct().getBrand() : null,
                            sp.getShop() != null ? sp.getShop().getId() : null,
                            sp.getShop() != null ? sp.getShop().getShopName() : null,
                            sp.getSellingPrice(),
                            sp.getActive()
                    ))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        }

    // ============================================================
    // GET BY ID
    // ============================================================

    @GetMapping("/{id}")
        public ResponseEntity<SellingPriceResponse> getById(
            @PathVariable Long id) {
        log.info("Fetching selling price with id: {}", id);
            var sp = service.getById(id);
            var resp = new SellingPriceResponse(
                    sp.getId(),
                    sp.getProductType() != null && sp.getProductType().getProduct() != null ? sp.getProductType().getProduct().getId() : null,
                    sp.getProductType() != null && sp.getProductType().getProduct() != null ? sp.getProductType().getProduct().getProductName() : null,
                    sp.getProductType() != null && sp.getProductType().getProduct() != null ? sp.getProductType().getProduct().getBrand() : null,
                    sp.getShop() != null ? sp.getShop().getId() : null,
                    sp.getShop() != null ? sp.getShop().getShopName() : null,
                    sp.getSellingPrice(),
                    sp.getActive()
            );
            return ResponseEntity.ok(resp);
        }

    // ============================================================
    // GET BY SHOP
    // ============================================================

    @GetMapping("/shop/{shopId}")
        public ResponseEntity<List<SellingPriceResponse>> getByShop(
            @PathVariable Long shopId) {
        log.info("Fetching selling prices for shop with id: {}", shopId);
            var list = service.getByShop(shopId);
            var responses = list.stream()
                    .map(sp -> new SellingPriceResponse(
                            sp.getId(),
                            sp.getProductType() != null && sp.getProductType().getProduct() != null ? sp.getProductType().getProduct().getId() : null,
                            sp.getProductType() != null && sp.getProductType().getProduct() != null ? sp.getProductType().getProduct().getProductName() : null,
                            sp.getProductType() != null && sp.getProductType().getProduct() != null ? sp.getProductType().getProduct().getBrand() : null,
                            sp.getShop() != null ? sp.getShop().getId() : null,
                            sp.getShop() != null ? sp.getShop().getShopName() : null,
                            sp.getSellingPrice(),
                            sp.getActive()
                    ))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        }

    // ============================================================
    // GET BY PRODUCT TYPE
    // ============================================================

    @GetMapping("/product-type/{productTypeId}")
        public ResponseEntity<List<SellingPriceResponse>> getByProductType(
            @PathVariable Long productTypeId) {
        log.info("Fetching selling prices for product type with id: {}", productTypeId);
            var list = service.getByProductType(productTypeId);
            var responses = list.stream()
                    .map(sp -> new SellingPriceResponse(
                            sp.getId(),
                            sp.getProductType() != null && sp.getProductType().getProduct() != null ? sp.getProductType().getProduct().getId() : null,
                            sp.getProductType() != null && sp.getProductType().getProduct() != null ? sp.getProductType().getProduct().getProductName() : null,
                            sp.getProductType() != null && sp.getProductType().getProduct() != null ? sp.getProductType().getProduct().getBrand() : null,
                            sp.getShop() != null ? sp.getShop().getId() : null,
                            sp.getShop() != null ? sp.getShop().getShopName() : null,
                            sp.getSellingPrice(),
                            sp.getActive()
                    ))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        }

    // ============================================================
    // GET BY SHOP + PRODUCT TYPE
    // ============================================================

    @GetMapping("/shop/{shopId}/product-type/{productTypeId}")
        public ResponseEntity<List<SellingPriceResponse>>
    getByShopAndProductType(
            @PathVariable Long shopId,
            @PathVariable Long productTypeId) {

        log.info("Fetching selling prices for shop with id: {} and product type with id: {}", shopId, productTypeId);
            var list = service.getByShopAndProductType(
                            shopId,
                            productTypeId
                    );
            var responses = list.stream()
                    .map(sp -> new SellingPriceResponse(
                            sp.getId(),
                            sp.getProductType() != null && sp.getProductType().getProduct() != null ? sp.getProductType().getProduct().getId() : null,
                            sp.getProductType() != null && sp.getProductType().getProduct() != null ? sp.getProductType().getProduct().getProductName() : null,
                            sp.getProductType() != null && sp.getProductType().getProduct() != null ? sp.getProductType().getProduct().getBrand() : null,
                            sp.getShop() != null ? sp.getShop().getId() : null,
                            sp.getShop() != null ? sp.getShop().getShopName() : null,
                            sp.getSellingPrice(),
                            sp.getActive()
                    ))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
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

        log.info("Fetching applicable selling price for product type with id: {} and shop with id: {}", productTypeId, shopId);
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
        public ResponseEntity<SellingPriceResponse> create(

            @RequestParam Long shopId,

            @RequestParam Long productTypeId,

            @RequestParam BigDecimal sellingPrice,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate effectiveFrom,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate effectiveTo) {
        log.info("Creating new selling price");
        SellingPrice result =
                service.create(
                        shopId,
                        productTypeId,
                        sellingPrice,
                        effectiveFrom,
                        effectiveTo
                );
        log.info("Creating new selling price");

            var resp = new SellingPriceResponse(
                    result.getId(),
                    result.getProductType() != null && result.getProductType().getProduct() != null ? result.getProductType().getProduct().getId() : null,
                    result.getProductType() != null && result.getProductType().getProduct() != null ? result.getProductType().getProduct().getProductName() : null,
                    result.getProductType() != null && result.getProductType().getProduct() != null ? result.getProductType().getProduct().getBrand() : null,
                    result.getShop() != null ? result.getShop().getId() : null,
                    result.getShop() != null ? result.getShop().getShopName() : null,
                    result.getSellingPrice(),
                    result.getActive()
            );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(resp);
        }

    // ============================================================
    // UPDATE
    // ============================================================

    @PutMapping("/{id}")
        public ResponseEntity<SellingPriceResponse> update(

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
        log.info("Updating selling price with id: {}", id);
            var resp = new SellingPriceResponse(
                    result.getId(),
                    result.getProductType() != null && result.getProductType().getProduct() != null ? result.getProductType().getProduct().getId() : null,
                    result.getProductType() != null && result.getProductType().getProduct() != null ? result.getProductType().getProduct().getProductName() : null,
                    result.getProductType() != null && result.getProductType().getProduct() != null ? result.getProductType().getProduct().getBrand() : null,
                    result.getShop() != null ? result.getShop().getId() : null,
                    result.getShop() != null ? result.getShop().getShopName() : null,
                    result.getSellingPrice(),
                    result.getActive()
            );
            return ResponseEntity.ok(resp);
        }

    // ============================================================
    // DELETE
    // ============================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        log.info("Deleting selling price with id: {}", id);
        service.delete(id);
        log.info("Selling price with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    // ============================================================
    // DEACTIVATE
    // ============================================================

    @PutMapping("/{id}/deactivate")
        public ResponseEntity<SellingPriceResponse> deactivate(
            @PathVariable Long id) {

        log.info("Deactivating selling price with id: {}", id);
            var result = service.deactivate(id);
            var resp = new SellingPriceResponse(
                    result.getId(),
                    result.getProductType() != null && result.getProductType().getProduct() != null ? result.getProductType().getProduct().getId() : null,
                    result.getProductType() != null && result.getProductType().getProduct() != null ? result.getProductType().getProduct().getProductName() : null,
                    result.getProductType() != null && result.getProductType().getProduct() != null ? result.getProductType().getProduct().getBrand() : null,
                    result.getShop() != null ? result.getShop().getId() : null,
                    result.getShop() != null ? result.getShop().getShopName() : null,
                    result.getSellingPrice(),
                    result.getActive()
            );
            return ResponseEntity.ok(resp);
        }
}