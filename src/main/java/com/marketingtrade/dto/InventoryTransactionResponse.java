package com.marketingtrade.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InventoryTransactionResponse {
    private final Long id;
    private final Long productTypeId;
    private final String transactionType;
    private final BigDecimal quantity;
    private final LocalDateTime transactionDate;
    private final Long referenceId;
    private final String notes;

    public InventoryTransactionResponse(Long id, Long productTypeId, String transactionType, BigDecimal quantity, LocalDateTime transactionDate, Long referenceId, String notes) {
        this.id = id;
        this.productTypeId = productTypeId;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
        this.referenceId = referenceId;
        this.notes = notes;
    }

    public Long getId() { return id; }
    public Long id() { return id; }

    public Long getProductTypeId() { return productTypeId; }
    public Long productTypeId() { return productTypeId; }

    public String getTransactionType() { return transactionType; }
    public String transactionType() { return transactionType; }

    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal quantity() { return quantity; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public LocalDateTime transactionDate() { return transactionDate; }

    public Long getReferenceId() { return referenceId; }
    public Long referenceId() { return referenceId; }

    public String getNotes() { return notes; }
    public String notes() { return notes; }
}