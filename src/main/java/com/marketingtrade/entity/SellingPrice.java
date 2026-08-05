package com.marketingtrade.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "shop_product_prices",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_shop_product_type_effective_from",
                        columnNames = {
                                "shop_id",
                                "product_type_id",
                                "effective_from"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class SellingPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    @Column(name = "selling_price", nullable = false,
            precision = 12, scale = 2)
    private BigDecimal sellingPrice;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @Column(nullable = false)
    private Boolean active = true;
}