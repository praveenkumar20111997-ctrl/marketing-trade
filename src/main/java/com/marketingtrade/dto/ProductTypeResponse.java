package com.marketingtrade.dto;

public record ProductTypeResponse(
        Long id,
        Long productId,
        String productName,
        String typeName,
        String specification,
        String unit,
        boolean active
) {
}