package com.marketingtrade.dto;

public record ShopResponse(
        Long id,
        String shopName,
        String ownerName,
        String contactNumber,
        String address,
        String location,
        Boolean active
) {
}