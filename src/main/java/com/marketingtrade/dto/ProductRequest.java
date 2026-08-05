package com.marketingtrade.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductRequest(

        @NotBlank
        String productName,

        String brand
) {
}