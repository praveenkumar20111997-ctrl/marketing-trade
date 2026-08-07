package com.marketingtrade.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class DeliveryItemRequest {
    @NotNull
    private Long productTypeId;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal quantity;

    public DeliveryItemRequest() {}

    public DeliveryItemRequest(Long productTypeId, BigDecimal quantity) {
        this.productTypeId = productTypeId;
        this.quantity = quantity;
    }

    public Long getProductTypeId() { return productTypeId; }
    public void setProductTypeId(Long productTypeId) { this.productTypeId = productTypeId; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

}