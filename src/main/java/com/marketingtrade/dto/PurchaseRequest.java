package com.marketingtrade.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class PurchaseRequest {
    private Long supplierId;
    @NotNull
    private LocalDate purchaseDate;
    private String invoiceNumber;
    private String notes;
    @NotEmpty
    private List<@Valid PurchaseItemRequest> items;

    public PurchaseRequest() {}

    public PurchaseRequest(Long supplierId, LocalDate purchaseDate, String invoiceNumber, String notes, List<PurchaseItemRequest> items) {
        this.supplierId = supplierId;
        this.purchaseDate = purchaseDate;
        this.invoiceNumber = invoiceNumber;
        this.notes = notes;
        this.items = items;
    }

    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }

    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public List<PurchaseItemRequest> getItems() { return items; }
    public void setItems(List<PurchaseItemRequest> items) { this.items = items; }

}