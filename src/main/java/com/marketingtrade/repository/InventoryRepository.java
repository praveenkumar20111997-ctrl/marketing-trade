package com.marketingtrade.repository;

import com.marketingtrade.entity.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface InventoryRepository
        extends JpaRepository<InventoryTransaction, Long> {

    @Query("""
        select coalesce(sum(i.quantity), 0)
        from InventoryTransaction i
        where i.productType.id = :productTypeId
        """)
    BigDecimal getStock(
            @Param("productTypeId") Long productTypeId);

    List<InventoryTransaction>
    findByProductTypeIdOrderByTransactionDateDesc(
            Long productTypeId);
}