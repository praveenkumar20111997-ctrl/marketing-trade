package com.marketingtrade.service;

import com.marketingtrade.entity.ProductType;
import com.marketingtrade.entity.Shop;
import com.marketingtrade.entity.SellingPrice;
import com.marketingtrade.dto.SellingPriceResponse;
import com.marketingtrade.repository.ProductTypeRepository;
import com.marketingtrade.repository.ShopRepository;
import com.marketingtrade.repository.SellingPriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
public class SellingPriceService {

    private final SellingPriceRepository sellingPriceRepository;
    private final ShopRepository shopRepository;
    private final ProductTypeRepository productTypeRepository;

    public SellingPriceService(
            SellingPriceRepository sellingPriceRepository,
            ShopRepository shopRepository,
            ProductTypeRepository productTypeRepository) {

        this.sellingPriceRepository = sellingPriceRepository;
        this.shopRepository = shopRepository;
        this.productTypeRepository = productTypeRepository;
    }

    // ============================================================
    // GET ALL
    // ============================================================

    @Transactional(readOnly = true)
    public java.util.List<SellingPrice> getAllEntities() {
        return sellingPriceRepository.findAll();
    }

    // ============================================================
    // GET BY ID
    // ============================================================

    @Transactional(readOnly = true)
    public SellingPrice getEntityById(Long id) {
        log.info("Fetching selling price with id: {}", id);
        return sellingPriceRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Selling price not found: " + id
                        )
                );
    }

    // ============================================================
    // GET BY SHOP
    // ============================================================

    @Transactional(readOnly = true)
    public java.util.List<SellingPrice> getByShopEntities(Long shopId) {
        log.info("Fetching selling prices for shop with id: {}", shopId);
        return sellingPriceRepository
                .findByShopId(shopId);
    }

    // ============================================================
    // GET BY PRODUCT TYPE
    // ============================================================

    @Transactional(readOnly = true)
    public java.util.List<SellingPrice> getByProductTypeEntities(
            Long productTypeId) {
        log.info("Fetching selling prices for product type with id: {}", productTypeId);
        return sellingPriceRepository
                .findByProductTypeId(productTypeId);
    }

    // ============================================================
    // GET BY SHOP + PRODUCT TYPE
    // ============================================================

    @Transactional(readOnly = true)
    public java.util.List<SellingPrice> getByShopAndProductTypeEntities(
            Long shopId,
            Long productTypeId) {
        log.info("Fetching selling prices for shop with id: {} and product type with id: {}", shopId, productTypeId);
        return sellingPriceRepository
                .findByShopIdAndProductTypeId(
                        shopId,
                        productTypeId
                );
    }

    // ============================================================
    // FIND APPLICABLE PRICE
    // ============================================================


    @Transactional(readOnly = true)
    public BigDecimal findApplicablePrice(
            Long productTypeId,
            Long shopId,
            LocalDate date) {

        log.info("Finding applicable selling price for product type with id: {}, shop with id: {}, and date: {}", productTypeId, shopId, date);

        // Create a final date so it can safely be used inside lambdas
        final LocalDate applicableDate =
                date != null ? date : LocalDate.now();

        List<SellingPrice> prices =
                sellingPriceRepository
                        .findByShopIdAndProductTypeIdAndActiveTrue(
                                shopId,
                                productTypeId
                        );

        return prices.stream()
                .filter(price ->
                        price.getEffectiveFrom() != null
                                && !price.getEffectiveFrom()
                                .isAfter(applicableDate)
                )
                .filter(price ->
                        price.getEffectiveTo() == null
                                || !price.getEffectiveTo()
                                .isBefore(applicableDate)
                )
                .sorted(
                        (a, b) ->
                                b.getEffectiveFrom()
                                        .compareTo(
                                                a.getEffectiveFrom()
                                        )
                )
                .map(SellingPrice::getSellingPrice)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "No applicable selling price found for "
                                        + "productTypeId="
                                        + productTypeId
                                        + ", shopId="
                                        + shopId
                                        + ", date="
                                        + applicableDate
                        )
                );
    }

    // ============================================================
    // CREATE
    // ============================================================

    public SellingPrice createEntity(
            Long shopId,
            Long productTypeId,
            BigDecimal sellingPrice,
            LocalDate effectiveFrom,
            LocalDate effectiveTo) {

        log.info("Creating new selling price for shop with id: {}, product type with id: {}", shopId, productTypeId);

        Shop shop = shopRepository
                .findById(shopId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Shop not found: " + shopId
                        )
                );

        ProductType productType =
                productTypeRepository
                        .findById(productTypeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product type not found: "
                                                + productTypeId
                                )
                        );

        if (sellingPrice == null
                || sellingPrice.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Selling price must be greater than zero"
            );
        }

        if (effectiveFrom == null) {
            effectiveFrom = LocalDate.now();
        }

        if (effectiveTo != null
                && effectiveTo.isBefore(effectiveFrom)) {

            throw new IllegalArgumentException(
                    "Effective-to date cannot be before effective-from date"
            );
        }

        SellingPrice entity = new SellingPrice();

        entity.setShop(shop);
        entity.setProductType(productType);
        entity.setSellingPrice(sellingPrice);
        entity.setEffectiveFrom(effectiveFrom);
        entity.setEffectiveTo(effectiveTo);
        entity.setActive(true);

        return sellingPriceRepository.save(entity);
    }


    // ============================================================
    // UPDATE
    // ============================================================

    public SellingPrice updateEntity(
            Long id,
            Long shopId,
            Long productTypeId,
            BigDecimal sellingPrice,
            LocalDate effectiveFrom,
            LocalDate effectiveTo,
            Boolean active) {

        log.info("Updating selling price with id: {}", id);
        SellingPrice entity = getEntityById(id);

        if (shopId != null) {

            Shop shop = shopRepository
                    .findById(shopId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Shop not found: " + shopId
                            )
                    );

            entity.setShop(shop);
        }

        if (productTypeId != null) {

            ProductType productType =
                    productTypeRepository
                            .findById(productTypeId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Product type not found: "
                                                    + productTypeId
                                    )
                            );

            entity.setProductType(productType);
        }

        if (sellingPrice != null) {

            if (sellingPrice.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(
                        "Selling price must be greater than zero"
                );
            }

            entity.setSellingPrice(sellingPrice);
        }

        if (effectiveFrom != null) {
            entity.setEffectiveFrom(effectiveFrom);
        }

        if (effectiveTo != null) {

            if (entity.getEffectiveFrom() != null
                    && effectiveTo.isBefore(
                    entity.getEffectiveFrom())) {

                throw new IllegalArgumentException(
                        "Effective-to date cannot be before effective-from date"
                );
            }

            entity.setEffectiveTo(effectiveTo);
        }

        if (active != null) {
            entity.setActive(active);
        }

        return sellingPriceRepository.save(entity);
    }

    // ============================================================
    // DELETE
    // ============================================================

    public void delete(Long id) {

        SellingPrice entity = getEntityById(id);

        sellingPriceRepository.delete(entity);
    }

    // ============================================================
    // DEACTIVATE
    // ============================================================

    public SellingPrice deactivateEntity(Long id) {

        SellingPrice entity = getEntityById(id);

        entity.setActive(false);

        log.info("Deactivating selling price with id: {}", id);
        return sellingPriceRepository.save(entity);
    }

    // -----------------------------
    // DTO returning methods for controllers
    // -----------------------------

    @Transactional(readOnly = true)
    public java.util.List<SellingPriceResponse> getAll() {
        return getAllEntities()
                .stream()
                .map(EntityDtoMapper::toSellingPriceResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SellingPriceResponse getById(Long id) {
        return EntityDtoMapper.toSellingPriceResponse(getEntityById(id));
    }

    @Transactional(readOnly = true)
    public java.util.List<SellingPriceResponse> getByShop(Long shopId) {
        return getByShopEntities(shopId)
                .stream()
                .map(EntityDtoMapper::toSellingPriceResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public java.util.List<SellingPriceResponse> getByProductType(Long productTypeId) {
        return getByProductTypeEntities(productTypeId)
                .stream()
                .map(EntityDtoMapper::toSellingPriceResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public java.util.List<SellingPriceResponse> getByShopAndProductType(Long shopId, Long productTypeId) {
        return getByShopAndProductTypeEntities(shopId, productTypeId)
                .stream()
                .map(EntityDtoMapper::toSellingPriceResponse)
                .toList();
    }

    public SellingPriceResponse create(
            Long shopId,
            Long productTypeId,
            BigDecimal sellingPrice,
            LocalDate effectiveFrom,
            LocalDate effectiveTo) {
        SellingPrice result = createEntity(shopId, productTypeId, sellingPrice, effectiveFrom, effectiveTo);
        return EntityDtoMapper.toSellingPriceResponse(result);
    }

    public SellingPriceResponse update(
            Long id,
            Long shopId,
            Long productTypeId,
            BigDecimal sellingPrice,
            LocalDate effectiveFrom,
            LocalDate effectiveTo,
            Boolean active) {
        SellingPrice result = updateEntity(id, shopId, productTypeId, sellingPrice, effectiveFrom, effectiveTo, active);
        return EntityDtoMapper.toSellingPriceResponse(result);
    }

    public SellingPriceResponse deactivate(Long id) {
        SellingPrice result = deactivateEntity(id);
        return EntityDtoMapper.toSellingPriceResponse(result);
    }
}
