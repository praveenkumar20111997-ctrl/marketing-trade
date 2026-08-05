package com.marketingtrade.repository;

import com.marketingtrade.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface PurchaseItemRepository
        extends JpaRepository<PurchaseItem, Long> {

    @Query("""
        select coalesce(
            sum(pi.quantity * pi.unitCost)
            / nullif(sum(pi.quantity), 0),
            0
        )
        from PurchaseItem pi
        where pi.productType.id = :productTypeId
          and pi.purchase.purchaseDate <= :date
        """)
    BigDecimal findAverageCost(
            @Param("productTypeId") Long productTypeId,
            @Param("date") java.time.LocalDate date
    );
}