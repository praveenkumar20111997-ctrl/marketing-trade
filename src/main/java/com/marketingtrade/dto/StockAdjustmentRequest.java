package com.marketingtrade.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record StockAdjustmentRequest(

        @NotNull
        BigDecimal quantity,

        String notes
) {
}