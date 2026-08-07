package com.marketingtrade.dto;

import jakarta.validation.constraints.NotBlank;

public class ShopRequest {
    @NotBlank
    private String shopName;
    private String ownerName;
    private String contactNumber;
    private String address;
    private String location;
    private Boolean active;

    public ShopRequest() {}

    public ShopRequest(String shopName, String ownerName, String contactNumber, String address, String location, Boolean active) {
        this.shopName = shopName;
        this.ownerName = ownerName;
        this.contactNumber = contactNumber;
        this.address = address;
        this.location = location;
        this.active = active;
    }

    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

}