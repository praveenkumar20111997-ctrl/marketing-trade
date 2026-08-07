package com.marketingtrade.service;

import com.marketingtrade.dto.StockResponse;
import com.marketingtrade.entity.InventoryTransaction;
import com.marketingtrade.entity.ProductType;
import com.marketingtrade.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class StockService {

    private final InventoryRepository inventoryRepository;
    private final ProductTypeService productTypeService;

    public StockService(
            InventoryRepository inventoryRepository,
            ProductTypeService productTypeService) {

        this.inventoryRepository = inventoryRepository;
        this.productTypeService = productTypeService;
    }

    public StockResponse getStock(Long productTypeId) {
        log.info("Getting stock for product type ID: {}", productTypeId);
        ProductType productType =
                productTypeService.findEntityById(productTypeId);

        BigDecimal stock =
                inventoryRepository.getStock(productTypeId);
        log.info("Retrieved stock for product type ID: {}: {}", productTypeId, stock);
        return toResponse(productType, stock);
    }

    public List<StockResponse> getAllStock() {
        log.info("Getting stock for all product types");

        return productTypeService
                .findAllEntities()
                .stream()
                .map(productType -> {

                    BigDecimal stock =
                            inventoryRepository
                                    .getStock(productType.getId());

                    return toResponse(
                            productType,
                            stock
                    );
                })
                .toList();
    }

    public java.util.List<com.marketingtrade.dto.InventoryTransactionResponse> history(
            Long productTypeId) {

        productTypeService.findEntityById(productTypeId);
        log.info("Fetching inventory history for product type ID: {}", productTypeId);

        return inventoryRepository
                .findByProductTypeIdOrderByTransactionDateDesc(
                        productTypeId
                )
                .stream()
                .map(EntityDtoMapper::toInventoryTransactionResponse)
                .toList();
    }

    @Transactional
    public com.marketingtrade.dto.InventoryTransactionResponse adjust(
            Long productTypeId,
            BigDecimal quantity,
            String notes) {
        log.info("Adjusting stock for product type ID: {}", productTypeId);
        ProductType productType =
                productTypeService.findEntityById(productTypeId);

        if (quantity == null ||
                quantity.compareTo(BigDecimal.ZERO) == 0) {

            throw new IllegalArgumentException(
                    "Adjustment quantity cannot be zero");
        }
        log.info("Current stock for product type ID: {}: {}", productTypeId, inventoryRepository.getStock(productTypeId));
        BigDecimal currentStock =
                inventoryRepository.getStock(productTypeId);

        BigDecimal nextStock =
                currentStock.add(quantity);

        if (nextStock.compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Insufficient stock. Current stock: "
                            + currentStock);
        }

        InventoryTransaction transaction =
                new InventoryTransaction();

        transaction.setProductType(productType);
        transaction.setTransactionType("ADJUSTMENT");
        transaction.setQuantity(quantity);
        transaction.setNotes(notes);
        log.info("Saving inventory transaction for product type ID: {}", productTypeId);
        var it = inventoryRepository.save(transaction);
        return EntityDtoMapper.toInventoryTransactionResponse(it);
    }

    private StockResponse toResponse(
            ProductType productType,
            BigDecimal quantity) {
        return new StockResponse(
                productType.getId(),
                productType.getProduct().getProductName(),
                productType.getTypeName(),
                productType.getSpecification(),
                productType.getUnit(),
                quantity
        );
    }
}