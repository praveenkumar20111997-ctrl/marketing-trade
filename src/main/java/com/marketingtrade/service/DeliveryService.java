package com.marketingtrade.service;

import com.marketingtrade.dto.DeliveryItemRequest;
import com.marketingtrade.dto.DeliveryRequest;
import com.marketingtrade.dto.DeliveryResponse;
import com.marketingtrade.entity.Delivery;
import com.marketingtrade.entity.DeliveryItem;
import com.marketingtrade.entity.InventoryTransaction;
import com.marketingtrade.entity.ProductType;
import com.marketingtrade.entity.Shop;
import com.marketingtrade.exception.ResourceNotFoundException;
import com.marketingtrade.repository.DeliveryRepository;
import com.marketingtrade.repository.InventoryRepository;
import com.marketingtrade.repository.ProductTypeRepository;
import com.marketingtrade.repository.PurchaseItemRepository;
import com.marketingtrade.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final ShopRepository shopRepository;
    private final ProductTypeRepository productTypeRepository;
    private final InventoryRepository inventoryRepository;
    private final SellingPriceService sellingPriceService;
    private final PurchaseItemRepository purchaseItemRepository;

    public DeliveryService(
            DeliveryRepository deliveryRepository,
            ShopRepository shopRepository,
            ProductTypeRepository productTypeRepository,
            InventoryRepository inventoryRepository,
            SellingPriceService sellingPriceService,
            PurchaseItemRepository purchaseItemRepository) {

        this.deliveryRepository = deliveryRepository;
        this.shopRepository = shopRepository;
        this.productTypeRepository = productTypeRepository;
        this.inventoryRepository = inventoryRepository;
        this.sellingPriceService = sellingPriceService;
        this.purchaseItemRepository = purchaseItemRepository;
    }

    public DeliveryResponse create(DeliveryRequest request) {

        LocalDate deliveryDate = request.deliveryDate();

        Shop shop = shopRepository
                .findById(request.shopId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Shop not found: "
                                        + request.shopId()));

        if (!shop.getActive()) {
            throw new IllegalArgumentException(
                    "Shop is inactive: " + shop.getId());
        }

        Delivery delivery = new Delivery();

        delivery.setShop(shop);
        delivery.setDeliveryDate(deliveryDate);
        delivery.setStatus("DELIVERED");
        delivery.setInvoiceNumber(request.invoiceNumber());
        delivery.setNotes(request.notes());

        for (DeliveryItemRequest requestItem : request.items()) {

            ProductType productType =
                    productTypeRepository
                            .findById(requestItem.productTypeId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Product type not found: "
                                                    + requestItem.productTypeId()));

            if (!productType.isActive()) {
                throw new IllegalArgumentException(
                        "Product type is inactive: "
                                + productType.getId());
            }

            BigDecimal quantity = requestItem.quantity();

            BigDecimal stock =
                    inventoryRepository.getStock(
                            productType.getId());

            if (stock == null) {
                stock = BigDecimal.ZERO;
            }

            if (stock.compareTo(quantity) < 0) {
                throw new IllegalArgumentException(
                        "Insufficient stock for "
                                + productType.getProduct().getProductName()
                                + " - "
                                + productType.getTypeName()
                                + ". Available: "
                                + stock);
            }

            BigDecimal sellingPrice =
                    sellingPriceService.findApplicablePrice(
                            productType.getId(),
                            shop.getId(),
                            deliveryDate
                    );


            BigDecimal purchaseCost =
                    purchaseItemRepository
                            .findAverageCost(
                                    productType.getId(),
                                    deliveryDate);

            if (purchaseCost == null) {
                purchaseCost = BigDecimal.ZERO;
            }

            BigDecimal totalSales =
                    quantity.multiply(sellingPrice);

            BigDecimal totalCost =
                    quantity.multiply(purchaseCost);

            BigDecimal profit =
                    totalSales.subtract(totalCost);

            DeliveryItem item = new DeliveryItem();

            item.setProductType(productType);
            item.setQuantity(quantity);
            item.setSellingPrice(sellingPrice);
            item.setPurchaseCost(purchaseCost);
            item.setTotalSales(totalSales);
            item.setTotalCost(totalCost);
            item.setProfit(profit);

            delivery.addItem(item);
        }

        /*
         * Save delivery first so the database generates delivery ID.
         */
        Delivery savedDelivery =
                deliveryRepository.saveAndFlush(delivery);

        /*
         * Reduce stock after delivery is saved.
         */
        log.info("Reducing stock for delivery ID: {}", savedDelivery.getId());
        for (DeliveryItem item : savedDelivery.getItems()) {

            InventoryTransaction transaction =
                    new InventoryTransaction();

            transaction.setProductType(
                    item.getProductType());

            transaction.setTransactionType("DELIVERY");

            transaction.setQuantity(
                    item.getQuantity().negate());

            transaction.setReferenceId(
                    savedDelivery.getId());

            transaction.setNotes(
                    "Delivery #"
                            + savedDelivery.getId());

            log.info("Creating inventory transaction for delivery ID: {}", savedDelivery.getId());
            inventoryRepository.save(transaction);
        }
        log.info("Delivery created with ID: {}", savedDelivery.getId());
        return toResponse(savedDelivery);
    }

    @Transactional(readOnly = true)
    public List<DeliveryResponse> findAll() {
        log.info("Fetching all deliveries");
        return deliveryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public DeliveryResponse findById(Long id) {
        log.info("Fetching delivery with ID: {}", id);
        Delivery delivery =
                deliveryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Delivery not found: "
                                                + id));

        return toResponse(delivery);
    }

    private DeliveryResponse toResponse(
            Delivery delivery) {
        log.info("Converting delivery to response: {}", delivery.getId());
        BigDecimal totalAmount = delivery.getItems()
                .stream()
                .map(DeliveryItem::getTotalSales)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCost = delivery.getItems()
                .stream()
                .map(DeliveryItem::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalProfit = delivery.getItems()
                .stream()
                .map(DeliveryItem::getProfit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<DeliveryResponse.DeliveryItemResponse> items =
                delivery.getItems()
                        .stream()
                        .map(item -> {

                            ProductType type =
                                    item.getProductType();

                            return new DeliveryResponse
                                    .DeliveryItemResponse(
                                    item.getId(),
                                    type.getId(),
                                    type.getProduct()
                                            .getProductName(),
                                    type.getTypeName(),
                                    type.getSpecification(),
                                    item.getQuantity(),
                                    item.getPurchaseCost(),
                                    item.getSellingPrice(),
                                    item.getTotalSales(),
                                    item.getTotalCost(),
                                    item.getProfit()
                            );
                        })
                        .toList();
        log.info("Delivery converted to response: {}", delivery.getId());
        return new DeliveryResponse(
                delivery.getId(),
                delivery.getShop().getId(),
                delivery.getShop().getShopName(),
                delivery.getDeliveryDate(),
                delivery.getStatus(),
                delivery.getInvoiceNumber(),
                totalAmount,
                totalCost,
                totalProfit,
                items
        );
    }
}