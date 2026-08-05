package com.marketingtrade.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_types")
@Getter
@Setter
@NoArgsConstructor
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "type_name", nullable = false, length = 100)
    private String typeName;

    @Column(length = 100)
    private String specification;

    @Column(nullable = false, length = 30)
    private String unit = "REAM";

    @Column(nullable = false)
    private boolean active = true;
}