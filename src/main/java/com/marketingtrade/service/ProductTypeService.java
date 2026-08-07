package com.marketingtrade.service;

import com.marketingtrade.dto.ProductTypeRequest;
import com.marketingtrade.dto.ProductTypeResponse;
import com.marketingtrade.entity.Product;
import com.marketingtrade.entity.ProductType;
import com.marketingtrade.exception.ResourceNotFoundException;
import com.marketingtrade.repository.ProductRepository;
import com.marketingtrade.repository.ProductTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ProductTypeService {

    private final ProductTypeRepository repository;
    private final ProductRepository productRepository;

    public ProductTypeService(
            ProductTypeRepository repository,
            ProductRepository productRepository) {

        this.repository = repository;
        this.productRepository = productRepository;
    }

    public List<ProductType> findAll() {
        log.info("Finding all product types");
        return repository.findAllWithProduct();
    }

    public List<ProductType> findByProduct(Long productId) {
        log.info("Finding product types for product ID: {}", productId);
        return repository.findByProduct_Id(productId);
    }

    public ProductType findById(Long id) {
        log.info("Finding product type with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product type not found: " + id));
    }

    @Transactional
    public ProductType create(ProductTypeRequest request) {

        ProductType productType = new ProductType();
        log.info("Creating new product type with name: {}", request.typeName());
        map(productType, request);

        return repository.save(productType);
    }

    @Transactional
    public ProductType update(
            Long id,
            ProductTypeRequest request) {

        ProductType productType = findById(id);
        log.info("Updating product type with ID: {}", productType.getId());

        map(productType, request);

        return repository.save(productType);
    }

    @Transactional
    public void deactivate(Long id) {

        ProductType productType = findById(id);

        productType.setActive(false);

        repository.save(productType);
    }

    private void map(
            ProductType productType,
            ProductTypeRequest request) {

        Product product = productRepository
                .findById(request.productId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found: "
                                        + request.productId()));

        productType.setProduct(product);
        productType.setTypeName(request.typeName());
        productType.setSpecification(request.specification());
        productType.setUnit(request.unit());

        if (request.active() != null) {
            productType.setActive(request.active());
        }
    }

    // -----------------------------
    // DTO returning methods for controllers
    // -----------------------------

    public java.util.List<ProductTypeResponse> findAllDto() {
        return findAll()
                .stream()
                .map(pt -> new ProductTypeResponse(
                        pt.getId(),
                        pt.getProduct() != null ? pt.getProduct().getId() : null,
                        pt.getProduct() != null ? pt.getProduct().getProductName() : null,
                        pt.getTypeName(),
                        pt.getSpecification(),
                        pt.getUnit(),
                        pt.isActive()
                ))
                .toList();
    }

    public java.util.List<ProductTypeResponse> findByProductDto(Long productId) {
        return findByProduct(productId)
                .stream()
                .map(pt -> new ProductTypeResponse(
                        pt.getId(),
                        pt.getProduct() != null ? pt.getProduct().getId() : null,
                        pt.getProduct() != null ? pt.getProduct().getProductName() : null,
                        pt.getTypeName(),
                        pt.getSpecification(),
                        pt.getUnit(),
                        pt.isActive()
                ))
                .toList();
    }

    public ProductTypeResponse findByIdDto(Long id) {
        ProductType pt = findById(id);
        return new ProductTypeResponse(
                pt.getId(),
                pt.getProduct() != null ? pt.getProduct().getId() : null,
                pt.getProduct() != null ? pt.getProduct().getProductName() : null,
                pt.getTypeName(),
                pt.getSpecification(),
                pt.getUnit(),
                pt.isActive()
        );
    }

    @Transactional
    public ProductTypeResponse createDto(ProductTypeRequest request) {
        ProductType pt = create(request);
        return findByIdDto(pt.getId());
    }

    @Transactional
    public ProductTypeResponse updateDto(Long id, ProductTypeRequest request) {
        ProductType pt = update(id, request);
        return findByIdDto(pt.getId());
    }
}