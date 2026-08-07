package com.marketingtrade.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PurchaseResponse {
    private final Long id;
    private final Long supplierId;
    private final String supplierName;
    private final LocalDate purchaseDate;
    private final String invoiceNumber;
    private final String notes;
    private final BigDecimal totalCost;
    private final List<PurchaseItemResponse> items;

    public PurchaseResponse(Long id, Long supplierId, String supplierName, LocalDate purchaseDate, String invoiceNumber, String notes, BigDecimal totalCost, List<PurchaseItemResponse> items) {
        this.id = id;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.purchaseDate = purchaseDate;
        this.invoiceNumber = invoiceNumber;
        this.notes = notes;
        this.totalCost = totalCost;
        this.items = items;
    }

    public Long getId() { return id; }

    public Long getSupplierId() { return supplierId; }

    public String getSupplierName() { return supplierName; }

    public LocalDate getPurchaseDate() { return purchaseDate; }

    public String getInvoiceNumber() { return invoiceNumber; }

    public String getNotes() { return notes; }

    public BigDecimal getTotalCost() { return totalCost; }

    public List<PurchaseItemResponse> getItems() { return items; }

    public static class PurchaseItemResponse {
        private final Long id;
        private final Long productTypeId;
        private final String productName;
        private final String typeName;
        private final String specification;
        private final BigDecimal quantity;
        private final BigDecimal unitCost;
        private final BigDecimal totalCost;

        public PurchaseItemResponse(Long id, Long productTypeId, String productName, String typeName, String specification, BigDecimal quantity, BigDecimal unitCost, BigDecimal totalCost) {
            this.id = id;
            this.productTypeId = productTypeId;
            this.productName = productName;
            this.typeName = typeName;
            this.specification = specification;
            this.quantity = quantity;
            this.unitCost = unitCost;
            this.totalCost = totalCost;
        }

        public Long getId() { return id; }

        public Long getProductTypeId() { return productTypeId; }

        public String getProductName() { return productName; }

        public String getTypeName() { return typeName; }

        public String getSpecification() { return specification; }

        public BigDecimal getQuantity() { return quantity; }

        public BigDecimal getUnitCost() { return unitCost; }

        public BigDecimal getTotalCost() { return totalCost; }
    }
}