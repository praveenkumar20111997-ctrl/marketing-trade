package com.marketingtrade.dto;

import jakarta.validation.constraints.NotBlank;

public class ProductRequest {
    @NotBlank
    private String productName;
    private String brand;

    public ProductRequest() {}

    public ProductRequest(String productName, String brand) {
        this.productName = productName;
        this.brand = brand;
    }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

}