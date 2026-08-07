package com.marketingtrade.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class StockAdjustmentRequest {
    @NotNull
    private BigDecimal quantity;
    private String notes;

    public StockAdjustmentRequest() {}

    public StockAdjustmentRequest(BigDecimal quantity, String notes) {
        this.quantity = quantity;
        this.notes = notes;
    }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

}