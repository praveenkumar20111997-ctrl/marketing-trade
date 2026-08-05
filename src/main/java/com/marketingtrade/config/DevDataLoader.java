package com.marketingtrade.config;

import com.marketingtrade.entity.*;
import com.marketingtrade.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DevDataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ShopRepository shopRepository;
    private final SupplierRepository supplierRepository;
    private final SellingPriceRepository sellingPriceRepository;
    private final InventoryRepository inventoryRepository;

    @Value("${app.seed.enabled:false}")
    private boolean seedEnabled;

    public DevDataLoader(
            ProductRepository productRepository,
            ProductTypeRepository productTypeRepository,
            ShopRepository shopRepository,
            SupplierRepository supplierRepository,
            SellingPriceRepository sellingPriceRepository,
            InventoryRepository inventoryRepository) {

        this.productRepository = productRepository;
        this.productTypeRepository = productTypeRepository;
        this.shopRepository = shopRepository;
        this.supplierRepository = supplierRepository;
        this.sellingPriceRepository = sellingPriceRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        if (!seedEnabled) {
            System.out.println(">>> Test data loader disabled");
            return;
        }

        System.out.println(">>> Loading Marketing Trade test data...");

        // ---------------------------------------------------------
        // PRODUCT
        // ---------------------------------------------------------

        Product product = createProduct();

        // ---------------------------------------------------------
        // PRODUCT TYPES
        // ---------------------------------------------------------

        ProductType type70 = createProductType(
                product,
                "TNPL 70 GSM",
                "A4 - 70 GSM"
        );

        ProductType type80 = createProductType(
                product,
                "TNPL 80 GSM",
                "A4 - 80 GSM"
        );

        // ---------------------------------------------------------
        // SHOPS
        // ---------------------------------------------------------

        Shop shop1 = createShop(
                "Kumar Stationery",
                "Kumar",
                "9876543210"
        );

        Shop shop2 = createShop(
                "Sri Lakshmi Stores",
                "Lakshmi",
                "9876501234"
        );

        // ---------------------------------------------------------
        // SUPPLIERS
        // ---------------------------------------------------------

        createSupplier(
                "TNPL Wholesale",
                "9876543210"
        );

        createSupplier(
                "Chennai Paper Traders",
                "9876501234"
        );

        // ---------------------------------------------------------
        // SELLING PRICES
        //
        // SellingPrice is based on:
        // Shop + ProductType
        // NOT Shop + Product
        // ---------------------------------------------------------

        createSellingPrice(
                type70,
                shop1,
                new BigDecimal("220.00")
        );

        createSellingPrice(
                type80,
                shop1,
                new BigDecimal("250.00")
        );

        createSellingPrice(
                type70,
                shop2,
                new BigDecimal("225.00")
        );

        createSellingPrice(
                type80,
                shop2,
                new BigDecimal("255.00")
        );

        // ---------------------------------------------------------
        // STOCK
        // ---------------------------------------------------------

        createStock(
                type70,
                new BigDecimal("100")
        );

        createStock(
                type80,
                new BigDecimal("75")
        );

        System.out.println(">>> Test data loading completed.");
    }

    // ============================================================
    // PRODUCT
    // ============================================================

    private Product createProduct() {

        return productRepository
                .findAll()
                .stream()
                .filter(p ->
                        "A4 Paper".equalsIgnoreCase(
                                p.getProductName()
                        )
                )
                .findFirst()
                .orElseGet(() -> {

                    Product product = new Product();

                    product.setProductName("A4 Paper");
                    product.setBrand("TNPL");
                    product.setActive(true);

                    return productRepository.save(product);
                });
    }

    // ============================================================
    // PRODUCT TYPE
    // ============================================================

    private ProductType createProductType(
            Product product,
            String typeName,
            String specification) {

        return productTypeRepository
                .findAll()
                .stream()
                .filter(pt ->
                        pt.getProduct() != null
                                && pt.getProduct().getId().equals(product.getId())
                                && pt.getTypeName() != null
                                && pt.getTypeName()
                                .equalsIgnoreCase(typeName)
                )
                .findFirst()
                .orElseGet(() -> {

                    ProductType type = new ProductType();

                    type.setProduct(product);
                    type.setTypeName(typeName);
                    type.setSpecification(specification);
                    type.setUnit("REAM");
                    type.setActive(true);

                    return productTypeRepository.save(type);
                });
    }

    // ============================================================
    // SHOP
    // ============================================================

    private Shop createShop(
            String shopName,
            String ownerName,
            String contactNumber) {

        return shopRepository
                .findAll()
                .stream()
                .filter(s ->
                        shopName.equalsIgnoreCase(
                                s.getShopName()
                        )
                )
                .findFirst()
                .orElseGet(() -> {

                    Shop shop = new Shop();

                    shop.setShopName(shopName);
                    shop.setOwnerName(ownerName);
                    shop.setContactNumber(contactNumber);
                    shop.setAddress("Chennai");
                    shop.setLocation("Chennai");
                    shop.setActive(true);

                    return shopRepository.save(shop);
                });
    }

    // ============================================================
    // SUPPLIER
    // ============================================================

    private Supplier createSupplier(
            String supplierName,
            String contact) {

        return supplierRepository
                .findAll()
                .stream()
                .filter(s ->
                        supplierName.equalsIgnoreCase(
                                s.getSupplierName()
                        )
                )
                .findFirst()
                .orElseGet(() -> {

                    Supplier supplier = new Supplier();

                    supplier.setSupplierName(supplierName);
                    supplier.setContact(contact);
                    supplier.setLocation("Chennai");
                    supplier.setAddress("Chennai");
                    supplier.setActive(true);

                    return supplierRepository.save(supplier);
                });
    }

    // ============================================================
    // SELLING PRICE
    // ============================================================

    private void createSellingPrice(
            ProductType productType,
            Shop shop,
            BigDecimal price) {

        boolean exists = sellingPriceRepository
                .findByShopId(shop.getId())
                .stream()
                .anyMatch(sp ->
                        sp.getProductType() != null
                                && sp.getProductType()
                                .getId()
                                .equals(productType.getId())
                                && sp.getEffectiveFrom() != null
                                && sp.getEffectiveFrom()
                                .equals(LocalDate.now())
                );

        if (!exists) {

            SellingPrice sellingPrice = new SellingPrice();

            sellingPrice.setShop(shop);
            sellingPrice.setProductType(productType);
            sellingPrice.setSellingPrice(price);
            sellingPrice.setEffectiveFrom(LocalDate.now());
            sellingPrice.setEffectiveTo(null);
            sellingPrice.setActive(true);

            sellingPriceRepository.save(sellingPrice);
        }
    }

    // ============================================================
    // STOCK
    // ============================================================

    private void createStock(
            ProductType productType,
            BigDecimal quantity) {

        BigDecimal current =
                inventoryRepository.getStock(
                        productType.getId()
                );

        if (current == null) {
            current = BigDecimal.ZERO;
        }

        if (current.compareTo(BigDecimal.ZERO) == 0) {

            InventoryTransaction transaction =
                    new InventoryTransaction();

            transaction.setProductType(productType);
            transaction.setTransactionType("TEST_SEED");
            transaction.setQuantity(quantity);
            transaction.setNotes("Development test stock");

            inventoryRepository.save(transaction);
        }
    }
}