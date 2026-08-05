package com.marketingtrade.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record DeliveryRequest(

        @NotNull
        Long shopId,

        @NotNull
        LocalDate deliveryDate,

        String invoiceNumber,

        String notes,

        @NotEmpty
        List<@Valid DeliveryItemRequest> items
) {
}