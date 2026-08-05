package com.marketingtrade.dto;

import jakarta.validation.constraints.NotBlank;

public record ShopRequest(
        @NotBlank String shopName,
        String ownerName,
        String contactNumber,
        String address,
        String location,
        Boolean active
) {
}