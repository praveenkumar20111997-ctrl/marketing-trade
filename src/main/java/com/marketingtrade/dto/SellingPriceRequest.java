package com.marketingtrade.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class SellingPriceRequest {
    @NotNull(message = "Product ID is required")
    private Long productId;
    @NotNull(message = "Shop ID is required")
    private Long shopId;
    @NotNull(message = "Selling price is required")
    @DecimalMin(value = "0.00", inclusive = true, message = "Selling price cannot be negative")
    private BigDecimal sellingPrice;
    private Boolean active;

    public SellingPriceRequest() {}

    public SellingPriceRequest(Long productId, Long shopId, BigDecimal sellingPrice, Boolean active) {
        this.productId = productId;
        this.shopId = shopId;
        this.sellingPrice = sellingPrice;
        this.active = active;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getShopId() { return shopId; }
    public void setShopId(Long shopId) { this.shopId = shopId; }

    public BigDecimal getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(BigDecimal sellingPrice) { this.sellingPrice = sellingPrice; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

}