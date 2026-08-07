package com.marketingtrade.dto;

import java.math.BigDecimal;

public class SellingPriceResponse {
    private final Long id;
    private final Long productId;
    private final String productName;
    private final String brand;
    private final Long shopId;
    private final String shopName;
    private final BigDecimal sellingPrice;
    private final Boolean active;

    public SellingPriceResponse(Long id, Long productId, String productName, String brand, Long shopId, String shopName, BigDecimal sellingPrice, Boolean active) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.shopId = shopId;
        this.shopName = shopName;
        this.sellingPrice = sellingPrice;
        this.active = active;
    }

    public Long getId() { return id; }

    public Long getProductId() { return productId; }

    public String getProductName() { return productName; }

    public String getBrand() { return brand; }

    public Long getShopId() { return shopId; }

    public String getShopName() { return shopName; }

    public BigDecimal getSellingPrice() { return sellingPrice; }

    public Boolean getActive() { return active; }
}