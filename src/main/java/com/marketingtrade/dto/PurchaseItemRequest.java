package com.marketingtrade.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PurchaseItemRequest {
    @NotNull
    private Long productTypeId;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal quantity;
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal unitCost;

    public PurchaseItemRequest() {}

    public PurchaseItemRequest(Long productTypeId, BigDecimal quantity, BigDecimal unitCost) {
        this.productTypeId = productTypeId;
        this.quantity = quantity;
        this.unitCost = unitCost;
    }

    public Long getProductTypeId() { return productTypeId; }
    public void setProductTypeId(Long productTypeId) { this.productTypeId = productTypeId; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }

}