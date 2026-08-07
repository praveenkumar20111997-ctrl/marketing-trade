package com.marketingtrade.dto;

public record SupplierResponse(
        Long id,
        String supplierName,
        String contact,
        String location,
        String address,
        Boolean active
) {
}