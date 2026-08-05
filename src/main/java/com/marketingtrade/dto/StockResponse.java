package com.marketingtrade.dto;

import java.math.BigDecimal;

public record StockResponse(

        Long productTypeId,

        String productName,

        String typeName,

        String specification,

        String unit,

        BigDecimal quantity
) {
}