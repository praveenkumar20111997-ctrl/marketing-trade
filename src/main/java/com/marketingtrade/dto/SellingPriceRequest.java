package com.marketingtrade.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SellingPriceRequest(

        @NotNull(message = "Product ID is required")
        Long productId,

        @NotNull(message = "Shop ID is required")
        Long shopId,

        @NotNull(message = "Selling price is required")
        @DecimalMin(
                value = "0.00",
                inclusive = true,
                message = "Selling price cannot be negative"
        )
        BigDecimal sellingPrice,

        Boolean active
) {
}