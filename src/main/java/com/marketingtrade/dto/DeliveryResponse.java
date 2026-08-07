package com.marketingtrade.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class DeliveryResponse {
    private final Long id;
    private final Long shopId;
    private final String shopName;
    private final LocalDate deliveryDate;
    private final String status;
    private final String invoiceNumber;
    private final BigDecimal totalAmount;
    private final BigDecimal totalCost;
    private final BigDecimal totalProfit;
    private final List<DeliveryItemResponse> items;

    public DeliveryResponse(Long id, Long shopId, String shopName, LocalDate deliveryDate, String status, String invoiceNumber, BigDecimal totalAmount, BigDecimal totalCost, BigDecimal totalProfit, List<DeliveryItemResponse> items) {
        this.id = id;
        this.shopId = shopId;
        this.shopName = shopName;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.invoiceNumber = invoiceNumber;
        this.totalAmount = totalAmount;
        this.totalCost = totalCost;
        this.totalProfit = totalProfit;
        this.items = items;
    }

    public Long getId() { return id; }
    public Long id() { return id; }

    public Long getShopId() { return shopId; }
    public Long shopId() { return shopId; }

    public String getShopName() { return shopName; }
    public String shopName() { return shopName; }

    public LocalDate getDeliveryDate() { return deliveryDate; }
    public LocalDate deliveryDate() { return deliveryDate; }

    public String getStatus() { return status; }
    public String status() { return status; }

    public String getInvoiceNumber() { return invoiceNumber; }
    public String invoiceNumber() { return invoiceNumber; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public BigDecimal totalAmount() { return totalAmount; }

    public BigDecimal getTotalCost() { return totalCost; }
    public BigDecimal totalCost() { return totalCost; }

    public BigDecimal getTotalProfit() { return totalProfit; }
    public BigDecimal totalProfit() { return totalProfit; }

    public List<DeliveryItemResponse> getItems() { return items; }
    public List<DeliveryItemResponse> items() { return items; }

    public static class DeliveryItemResponse {
        private final Long id;
        private final Long productTypeId;
        private final String productName;
        private final String typeName;
        private final String specification;
        private final BigDecimal quantity;
        private final BigDecimal purchaseCost;
        private final BigDecimal sellingPrice;
        private final BigDecimal totalSales;
        private final BigDecimal totalCost;
        private final BigDecimal profit;

        public DeliveryItemResponse(Long id, Long productTypeId, String productName, String typeName, String specification, BigDecimal quantity, BigDecimal purchaseCost, BigDecimal sellingPrice, BigDecimal totalSales, BigDecimal totalCost, BigDecimal profit) {
            this.id = id;
            this.productTypeId = productTypeId;
            this.productName = productName;
            this.typeName = typeName;
            this.specification = specification;
            this.quantity = quantity;
            this.purchaseCost = purchaseCost;
            this.sellingPrice = sellingPrice;
            this.totalSales = totalSales;
            this.totalCost = totalCost;
            this.profit = profit;
        }

        public Long getId() { return id; }
        public Long id() { return id; }

        public Long getProductTypeId() { return productTypeId; }
        public Long productTypeId() { return productTypeId; }

        public String getProductName() { return productName; }
        public String productName() { return productName; }

        public String getTypeName() { return typeName; }
        public String typeName() { return typeName; }

        public String getSpecification() { return specification; }
        public String specification() { return specification; }

        public BigDecimal getQuantity() { return quantity; }
        public BigDecimal quantity() { return quantity; }

        public BigDecimal getPurchaseCost() { return purchaseCost; }
        public BigDecimal purchaseCost() { return purchaseCost; }

        public BigDecimal getSellingPrice() { return sellingPrice; }
        public BigDecimal sellingPrice() { return sellingPrice; }

        public BigDecimal getTotalSales() { return totalSales; }
        public BigDecimal totalSales() { return totalSales; }

        public BigDecimal getTotalCost() { return totalCost; }
        public BigDecimal totalCost() { return totalCost; }

        public BigDecimal getProfit() { return profit; }
        public BigDecimal profit() { return profit; }
    }
}