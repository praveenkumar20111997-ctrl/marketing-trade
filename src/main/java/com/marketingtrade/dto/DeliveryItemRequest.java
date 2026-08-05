package com.marketingtrade.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DeliveryItemRequest(

        @NotNull
        Long productTypeId,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal quantity
) {
}