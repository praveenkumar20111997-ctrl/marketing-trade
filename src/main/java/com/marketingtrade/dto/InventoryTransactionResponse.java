package com.marketingtrade.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InventoryTransactionResponse(
        Long id,
        Long productTypeId,
        String transactionType,
        BigDecimal quantity,
        LocalDateTime transactionDate,
        Long referenceId,
        String notes
) {
}