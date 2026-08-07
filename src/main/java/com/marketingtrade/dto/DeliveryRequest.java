package com.marketingtrade.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class DeliveryRequest {
    @NotNull
    private Long shopId;
    @NotNull
    private LocalDate deliveryDate;
    private String invoiceNumber;
    private String notes;
    @NotEmpty
    private List<@Valid DeliveryItemRequest> items;

    public DeliveryRequest() {}

    public DeliveryRequest(Long shopId, LocalDate deliveryDate, String invoiceNumber, String notes, List<DeliveryItemRequest> items) {
        this.shopId = shopId;
        this.deliveryDate = deliveryDate;
        this.invoiceNumber = invoiceNumber;
        this.notes = notes;
        this.items = items;
    }

    public Long getShopId() { return shopId; }
    public void setShopId(Long shopId) { this.shopId = shopId; }

    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }

    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public List<DeliveryItemRequest> getItems() { return items; }
    public void setItems(List<DeliveryItemRequest> items) { this.items = items; }

}