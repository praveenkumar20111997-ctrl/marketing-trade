package com.marketingtrade.dto;

public class ShopResponse {
    private final Long id;
    private final String shopName;
    private final String ownerName;
    private final String contactNumber;
    private final String address;
    private final String location;
    private final Boolean active;

    public ShopResponse(Long id, String shopName, String ownerName, String contactNumber, String address, String location, Boolean active) {
        this.id = id;
        this.shopName = shopName;
        this.ownerName = ownerName;
        this.contactNumber = contactNumber;
        this.address = address;
        this.location = location;
        this.active = active;
    }

    public Long getId() { return id; }

    public String getShopName() { return shopName; }

    public String getOwnerName() { return ownerName; }

    public String getContactNumber() { return contactNumber; }

    public String getAddress() { return address; }

    public String getLocation() { return location; }

    public Boolean getActive() { return active; }
}