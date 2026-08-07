package com.marketingtrade.dto;

import jakarta.validation.constraints.NotBlank;

public class SupplierRequest {
    @NotBlank
    private String supplierName;
    private String contact;
    private String location;
    private String address;
    private Boolean active;

    public SupplierRequest() {}

    public SupplierRequest(String supplierName, String contact, String location, String address, Boolean active) {
        this.supplierName = supplierName;
        this.contact = contact;
        this.location = location;
        this.address = address;
        this.active = active;
    }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

}