package com.marketingtrade.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record PurchaseRequest(

        Long supplierId,

        @NotNull
        LocalDate purchaseDate,

        String invoiceNumber,

        String notes,

        @NotEmpty
        List<@Valid PurchaseItemRequest> items
) {
}