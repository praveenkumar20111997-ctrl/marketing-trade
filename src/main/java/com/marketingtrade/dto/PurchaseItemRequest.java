package com.marketingtrade.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PurchaseItemRequest(

        @NotNull
        Long productTypeId,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal quantity,

        @NotNull
        @DecimalMin("0.00")
        BigDecimal unitCost
) {
}