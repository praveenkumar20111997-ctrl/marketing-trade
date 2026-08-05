package com.marketingtrade.dto;

import jakarta.validation.constraints.NotBlank;

public record SupplierRequest(
        @NotBlank String supplierName,
        String contact,
        String location,
        String address,
        Boolean active
) {
}