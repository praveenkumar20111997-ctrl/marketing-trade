package com.marketingtrade.dto;

public class ProductTypeResponse {
    private final Long id;
    private final Long productId;
    private final String productName;
    private final String typeName;
    private final String specification;
    private final String unit;
    private final boolean active;

    public ProductTypeResponse(Long id, Long productId, String productName, String typeName, String specification, String unit, boolean active) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.typeName = typeName;
        this.specification = specification;
        this.unit = unit;
        this.active = active;
    }

    public Long getId() { return id; }
    public Long id() { return id; }

    public Long getProductId() { return productId; }
    public Long productId() { return productId; }

    public String getProductName() { return productName; }
    public String productName() { return productName; }

    public String getTypeName() { return typeName; }
    public String typeName() { return typeName; }

    public String getSpecification() { return specification; }
    public String specification() { return specification; }

    public String getUnit() { return unit; }
    public String unit() { return unit; }

    public boolean getActive() { return active; }
    public boolean active() { return active; }
}