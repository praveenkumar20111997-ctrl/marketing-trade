package com.marketingtrade.service;

import com.marketingtrade.dto.*;
import com.marketingtrade.entity.*;

public final class EntityDtoMapper {

    private EntityDtoMapper() {}

    public static ProductResponse toProductResponse(Product p) {
        if (p == null) return null;
        return new ProductResponse(p.getId(), p.getProductName(), p.getBrand(), p.isActive());
    }

    public static ShopResponse toShopResponse(Shop s) {
        if (s == null) return null;
        return new ShopResponse(s.getId(), s.getShopName(), s.getOwnerName(), s.getContactNumber(), s.getAddress(), s.getLocation(), s.getActive());
    }

    public static SupplierResponse toSupplierResponse(Supplier s) {
        if (s == null) return null;
        return new SupplierResponse(s.getId(), s.getSupplierName(), s.getContact(), s.getLocation(), s.getAddress(), s.getActive());
    }

    public static ProductTypeResponse toProductTypeResponse(ProductType pt) {
        if (pt == null) return null;
        Long productId = pt.getProduct() != null ? pt.getProduct().getId() : null;
        String productName = pt.getProduct() != null ? pt.getProduct().getProductName() : null;
        return new ProductTypeResponse(pt.getId(), productId, productName, pt.getTypeName(), pt.getSpecification(), pt.getUnit(), pt.isActive());
    }

    public static SellingPriceResponse toSellingPriceResponse(SellingPrice sp) {
        if (sp == null) return null;
        Long productId = null;
        String productName = null;
        String brand = null;
        if (sp.getProductType() != null && sp.getProductType().getProduct() != null) {
            productId = sp.getProductType().getProduct().getId();
            productName = sp.getProductType().getProduct().getProductName();
            brand = sp.getProductType().getProduct().getBrand();
        }
        Long shopId = sp.getShop() != null ? sp.getShop().getId() : null;
        String shopName = sp.getShop() != null ? sp.getShop().getShopName() : null;
        return new SellingPriceResponse(sp.getId(), productId, productName, brand, shopId, shopName, sp.getSellingPrice(), sp.getActive());
    }

    public static com.marketingtrade.dto.InventoryTransactionResponse toInventoryTransactionResponse(InventoryTransaction it) {
        if (it == null) return null;
        Long productTypeId = it.getProductType() != null ? it.getProductType().getId() : null;
        return new com.marketingtrade.dto.InventoryTransactionResponse(it.getId(), productTypeId, it.getTransactionType(), it.getQuantity(), it.getTransactionDate(), it.getReferenceId(), it.getNotes());
    }
}
