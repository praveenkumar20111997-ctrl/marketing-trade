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

    // Entity-returning methods (internal use)
    public java.util.List<ProductType> findAllEntities() {
        log.info("Finding all product types");
        return repository.findAllWithProduct();
    }

    public java.util.List<ProductType> findByProductEntities(Long productId) {
        log.info("Finding product types for product ID: {}", productId);
        return repository.findByProduct_Id(productId);
    }

    public ProductType findEntityById(Long id) {
        log.info("Finding product type with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product type not found: " + id));
    }

    @Transactional
    public ProductType createEntity(ProductTypeRequest request) {

        ProductType productType = new ProductType();
        log.info("Creating new product type with name: {}", request.getTypeName());
        map(productType, request);

        return repository.save(productType);
    }

    @Transactional
    public ProductType updateEntity(
            Long id,
            ProductTypeRequest request) {

        ProductType productType = findEntityById(id);
        log.info("Updating product type with ID: {}", productType.getId());

        map(productType, request);

        return repository.save(productType);
    }

    @Transactional
    public void deactivateEntity(Long id) {

        ProductType productType = findEntityById(id);

        productType.setActive(false);

        repository.save(productType);
    }

    @Transactional
    public void deactivate(Long id) {
        deactivateEntity(id);
    }

    private void map(
            ProductType productType,
            ProductTypeRequest request) {

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found: "
                                        + request.getProductId()));

        productType.setProduct(product);
        productType.setTypeName(request.getTypeName());
        productType.setSpecification(request.getSpecification());
        productType.setUnit(request.getUnit());

        if (request.getActive() != null) {
            productType.setActive(request.getActive());
        }
    }

    // -----------------------------
    // DTO returning methods for controllers
    // -----------------------------

    public java.util.List<ProductTypeResponse> findAll() {
        return findAllEntities()
                .stream()
                .map(EntityDtoMapper::toProductTypeResponse)
                .toList();
    }

    public java.util.List<ProductTypeResponse> findByProduct(Long productId) {
        return findByProductEntities(productId)
                .stream()
                .map(EntityDtoMapper::toProductTypeResponse)
                .toList();
    }

    public ProductTypeResponse findById(Long id) {
        ProductType pt = findEntityById(id);
        return EntityDtoMapper.toProductTypeResponse(pt);
    }

    @Transactional
    public ProductTypeResponse create(ProductTypeRequest request) {
        ProductType pt = createEntity(request);
        return findById(pt.getId());
    }

    @Transactional
    public ProductTypeResponse update(Long id, ProductTypeRequest request) {
        ProductType pt = updateEntity(id, request);
        return findById(pt.getId());
    }
}
