package com.marketingtrade.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_trips")
@Getter
@Setter
@NoArgsConstructor
public class DeliveryTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trip_date", nullable = false)
    private LocalDate tripDate;

    @Column(name = "vehicle_type", length = 50)
    private String vehicleType;

    @Column(name = "vehicle_rent", nullable = false,
            precision = 12, scale = 2)
    private BigDecimal vehicleRent = BigDecimal.ZERO;

    @Column(name = "fuel_cost", nullable = false,
            precision = 12, scale = 2)
    private BigDecimal fuelCost = BigDecimal.ZERO;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void prePersist() {
        createdAt = LocalDateTime.now();
    }
}