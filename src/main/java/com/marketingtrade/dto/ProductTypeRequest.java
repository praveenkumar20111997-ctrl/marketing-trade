package com.marketingtrade.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductTypeRequest {
    @NotNull
    private Long productId;
    @NotBlank
    private String typeName;
    private String specification;
    @NotBlank
    private String unit;
    private Boolean active;

    public ProductTypeRequest() {}

    public ProductTypeRequest(Long productId, String typeName, String specification, String unit, Boolean active) {
        this.productId = productId;
        this.typeName = typeName;
        this.specification = specification;
        this.unit = unit;
        this.active = active;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }

    public String getSpecification() { return specification; }
    public void setSpecification(String specification) { this.specification = specification; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

}