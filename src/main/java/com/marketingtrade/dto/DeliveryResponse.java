package com.marketingtrade.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DeliveryResponse(

        Long id,

        Long shopId,

        String shopName,

        LocalDate deliveryDate,

        String status,

        String invoiceNumber,

        BigDecimal totalAmount,

        BigDecimal totalCost,

        BigDecimal totalProfit,

        List<DeliveryItemResponse> items
) {

    public record DeliveryItemResponse(

            Long id,

            Long productTypeId,

            String productName,

            String typeName,

            String specification,

            BigDecimal quantity,

            BigDecimal purchaseCost,

            BigDecimal sellingPrice,

            BigDecimal totalSales,

            BigDecimal totalCost,

            BigDecimal profit
    ) {
    }
}