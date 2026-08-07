package com.marketingtrade.dto;

public class ProductResponse {
    private final Long id;
    private final String productName;
    private final String brand;
    private final boolean active;

    public ProductResponse(Long id, String productName, String brand, boolean active) {
        this.id = id;
        this.productName = productName;
        this.brand = brand;
        this.active = active;
    }

    public Long getId() { return id; }
    public Long id() { return id; }

    public String getProductName() { return productName; }
    public String productName() { return productName; }

    public String getBrand() { return brand; }
    public String brand() { return brand; }

    public boolean getActive() { return active; }
    public boolean active() { return active; }
}