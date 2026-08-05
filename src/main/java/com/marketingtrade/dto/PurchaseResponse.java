package com.marketingtrade.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PurchaseResponse(

        Long id,

        Long supplierId,

        String supplierName,

        LocalDate purchaseDate,

        String invoiceNumber,

        String notes,

        BigDecimal totalCost,

        List<PurchaseItemResponse> items
) {

    public record PurchaseItemResponse(

            Long id,

            Long productTypeId,

            String productName,

            String typeName,

            String specification,

            BigDecimal quantity,

            BigDecimal unitCost,

            BigDecimal totalCost
    ) {
    }
}