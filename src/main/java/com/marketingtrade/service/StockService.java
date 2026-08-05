package com.marketingtrade.service;

import com.marketingtrade.dto.StockResponse;
import com.marketingtrade.entity.InventoryTransaction;
import com.marketingtrade.entity.ProductType;
import com.marketingtrade.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

        ProductType productType =
                productTypeService.findById(productTypeId);

        BigDecimal stock =
                inventoryRepository.getStock(productTypeId);

        return toResponse(productType, stock);
    }

    public List<StockResponse> getAllStock() {

        return productTypeService
                .findAll()
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

    public List<InventoryTransaction> history(
            Long productTypeId) {

        productTypeService.findById(productTypeId);

        return inventoryRepository
                .findByProductTypeIdOrderByTransactionDateDesc(
                        productTypeId
                );
    }

    @Transactional
    public InventoryTransaction adjust(
            Long productTypeId,
            BigDecimal quantity,
            String notes) {

        ProductType productType =
                productTypeService.findById(productTypeId);

        if (quantity == null ||
                quantity.compareTo(BigDecimal.ZERO) == 0) {

            throw new IllegalArgumentException(
                    "Adjustment quantity cannot be zero");
        }

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

        return inventoryRepository.save(transaction);
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