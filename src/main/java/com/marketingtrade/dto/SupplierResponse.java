package com.marketingtrade.dto;

public class SupplierResponse {
    private final Long id;
    private final String supplierName;
    private final String contact;
    private final String location;
    private final String address;
    private final Boolean active;

    public SupplierResponse(Long id, String supplierName, String contact, String location, String address, Boolean active) {
        this.id = id;
        this.supplierName = supplierName;
        this.contact = contact;
        this.location = location;
        this.address = address;
        this.active = active;
    }

    public Long getId() { return id; }

    public String getSupplierName() { return supplierName; }

    public String getContact() { return contact; }

    public String getLocation() { return location; }

    public String getAddress() { return address; }

    public Boolean getActive() { return active; }
}