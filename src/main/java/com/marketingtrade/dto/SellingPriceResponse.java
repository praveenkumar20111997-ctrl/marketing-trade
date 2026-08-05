package com.marketingtrade.dto;

import java.math.BigDecimal;

public record SellingPriceResponse(

        Long id,

        Long productId,

        String productName,

        String brand,

        Long shopId,

        String shopName,

        BigDecimal sellingPrice,

        Boolean active

) {
}