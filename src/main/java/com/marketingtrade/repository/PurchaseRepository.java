package com.marketingtrade.repository;

import com.marketingtrade.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository
        extends JpaRepository<Purchase, Long> {

    List<Purchase> findByPurchaseDateBetween(
            LocalDate from,
            LocalDate to
    );
}