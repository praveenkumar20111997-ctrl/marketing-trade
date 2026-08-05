package com.marketingtrade.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "delivery_items")
@Getter
@Setter
@NoArgsConstructor
public class DeliveryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal quantity;

    @Column(name = "selling_price", nullable = false,
            precision = 12, scale = 2)
    private BigDecimal sellingPrice;

    @Column(name = "purchase_cost", nullable = false,
            precision = 12, scale = 2)
    private BigDecimal purchaseCost;

    @Column(name = "total_sales", nullable = false,
            precision = 14, scale = 2)
    private BigDecimal totalSales;

    @Column(name = "total_cost", nullable = false,
            precision = 14, scale = 2)
    private BigDecimal totalCost;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal profit;
}