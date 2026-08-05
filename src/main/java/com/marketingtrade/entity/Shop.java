package com.marketingtrade.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "shops")
@Getter
@Setter
@NoArgsConstructor
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_name", nullable = false, length = 150)
    private String shopName;

    @Column(name = "owner_name", length = 100)
    private String ownerName;

    @Column(name = "contact_number", length = 30)
    private String contactNumber;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(length = 150)
    private String location;

    @Column(nullable = false)
    private Boolean active = true;
}