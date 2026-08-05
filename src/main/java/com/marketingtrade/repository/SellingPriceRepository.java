package com.marketingtrade.repository;

import com.marketingtrade.entity.SellingPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SellingPriceRepository
        extends JpaRepository<SellingPrice, Long> {

    List<SellingPrice> findByShopId(Long shopId);

    List<SellingPrice> findByProductTypeId(Long productTypeId);

    List<SellingPrice> findByShopIdAndProductTypeId(
            Long shopId,
            Long productTypeId
    );

    List<SellingPrice> findByShopIdAndProductTypeIdAndActiveTrue(
            Long shopId,
            Long productTypeId
    );

    Optional<SellingPrice> findByShopIdAndProductTypeIdAndEffectiveFrom(
            Long shopId,
            Long productTypeId,
            LocalDate effectiveFrom
    );

    List<SellingPrice> findByActiveTrue();
}