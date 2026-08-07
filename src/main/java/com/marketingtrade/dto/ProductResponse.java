package com.marketingtrade.dto;

public record ProductResponse(
        Long id,
        String productName,
        String brand,
        boolean active
) {
}