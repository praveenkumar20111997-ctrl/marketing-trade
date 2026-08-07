package com.marketingtrade.dto;

import java.math.BigDecimal;

public class StockResponse {
    private final Long productTypeId;
    private final String productName;
    private final String typeName;
    private final String specification;
    private final String unit;
    private final BigDecimal quantity;

    public StockResponse(Long productTypeId, String productName, String typeName, String specification, String unit, BigDecimal quantity) {
        this.productTypeId = productTypeId;
        this.productName = productName;
        this.typeName = typeName;
        this.specification = specification;
        this.unit = unit;
        this.quantity = quantity;
    }

    public Long getProductTypeId() { return productTypeId; }

    public String getProductName() { return productName; }

    public String getTypeName() { return typeName; }

    public String getSpecification() { return specification; }

    public String getUnit() { return unit; }

    public BigDecimal getQuantity() { return quantity; }
}