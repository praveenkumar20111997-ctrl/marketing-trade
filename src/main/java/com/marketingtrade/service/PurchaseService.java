package com.marketingtrade.service;

import com.marketingtrade.dto.PurchaseItemRequest;
import com.marketingtrade.dto.PurchaseRequest;
import com.marketingtrade.dto.PurchaseResponse;
import com.marketingtrade.entity.InventoryTransaction;
import com.marketingtrade.entity.ProductType;
import com.marketingtrade.entity.Purchase;
import com.marketingtrade.entity.PurchaseItem;
import com.marketingtrade.entity.Supplier;
import com.marketingtrade.exception.ResourceNotFoundException;
import com.marketingtrade.repository.InventoryRepository;
import com.marketingtrade.repository.PurchaseRepository;
import com.marketingtrade.repository.SupplierRepository;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final ProductTypeService productTypeService;
    private final InventoryRepository inventoryRepository;

    public PurchaseService(
            PurchaseRepository purchaseRepository,
            SupplierRepository supplierRepository,
            ProductTypeService productTypeService,
            InventoryRepository inventoryRepository) {

        this.purchaseRepository = purchaseRepository;
        this.supplierRepository = supplierRepository;
        this.productTypeService = productTypeService;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public List<PurchaseResponse> findAll() {

        return purchaseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PurchaseResponse findById(Long id) {

        Purchase purchase =
                purchaseRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Purchase not found: "
                                                + id));

        return toResponse(purchase);
    }

    @Transactional(readOnly = true)
    public List<PurchaseResponse> findByDate(
            LocalDate from,
            LocalDate to) {

        return purchaseRepository
                .findByPurchaseDateBetween(from, to)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PurchaseResponse create(
            PurchaseRequest request) {

        Purchase purchase = new Purchase();

        purchase.setPurchaseDate(
                request.purchaseDate());

        purchase.setInvoiceNumber(
                request.invoiceNumber());

        purchase.setNotes(
                request.notes());

        if (request.supplierId() != null) {

            Supplier supplier =
                    supplierRepository
                            .findById(request.supplierId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Supplier not found: "
                                                    + request.supplierId()));

            if (!supplier.getActive()) {
                throw new IllegalArgumentException(
                        "Supplier is inactive: "
                                + supplier.getId());
            }

            purchase.setSupplier(supplier);
        }

        for (PurchaseItemRequest requestItem :
                request.items()) {

            ProductType productType =
                    productTypeService.findById(
                            requestItem.productTypeId());

            if (!productType.isActive()) {
                throw new IllegalArgumentException(
                        "Product type is inactive: "
                                + productType.getId());
            }

            BigDecimal quantity =
                    requestItem.quantity();

            BigDecimal unitCost =
                    requestItem.unitCost();

            PurchaseItem item = new PurchaseItem();

            item.setProductType(productType);
            item.setQuantity(quantity);
            item.setUnitCost(unitCost);
            item.setTotalCost(
                    quantity.multiply(unitCost));

            purchase.addItem(item);
        }

        Purchase savedPurchase =
                purchaseRepository.saveAndFlush(purchase);

        for (PurchaseItem item :
                savedPurchase.getItems()) {

            InventoryTransaction transaction = createTransaction(item, savedPurchase);

            inventoryRepository.save(transaction);
        }

        return toResponse(savedPurchase);
    }

    @Nonnull
    private static InventoryTransaction createTransaction(PurchaseItem item, Purchase savedPurchase) {
        InventoryTransaction transaction =
                new InventoryTransaction();

        transaction.setProductType(
                item.getProductType());

        transaction.setTransactionType(
                "PURCHASE");

        transaction.setQuantity(
                item.getQuantity());

        transaction.setReferenceId(
                savedPurchase.getId());

        transaction.setNotes(
                "Purchase #"
                        + savedPurchase.getId());
        return transaction;
    }

    private PurchaseResponse toResponse(
            Purchase purchase) {

        List<PurchaseResponse.PurchaseItemResponse>
                items = purchase.getItems()
                .stream()
                .map(item -> {

                    ProductType type =
                            item.getProductType();

                    return new PurchaseResponse
                            .PurchaseItemResponse(
                            item.getId(),
                            type.getId(),
                            type.getProduct()
                                    .getProductName(),
                            type.getTypeName(),
                            type.getSpecification(),
                            item.getQuantity(),
                            item.getUnitCost(),
                            item.getTotalCost()
                    );
                })
                .toList();

        BigDecimal totalCost = items.stream()
                .map(PurchaseResponse.PurchaseItemResponse
                        ::totalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Long supplierId =
                purchase.getSupplier() == null
                        ? null
                        : purchase.getSupplier().getId();

        String supplierName =
                purchase.getSupplier() == null
                        ? null
                        : purchase.getSupplier()
                        .getSupplierName();

        return new PurchaseResponse(
                purchase.getId(),
                supplierId,
                supplierName,
                purchase.getPurchaseDate(),
                purchase.getInvoiceNumber(),
                purchase.getNotes(),
                totalCost,
                items
        );
    }
}