package com.marketingtrade.service;

import com.marketingtrade.dto.SupplierRequest;
import com.marketingtrade.dto.SupplierResponse;
import com.marketingtrade.entity.Supplier;
import com.marketingtrade.exception.ResourceNotFoundException;
import com.marketingtrade.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class SupplierService {

    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public java.util.List<Supplier> getAllEntities() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Supplier getEntityById(Long id) {
        log.info("Finding supplier with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Supplier not found: " + id));
    }

    @Transactional
    public Supplier createEntity(SupplierRequest request) {

        Supplier supplier = new Supplier();

        supplier.setSupplierName(request.getSupplierName());
        supplier.setContact(request.getContact());
        supplier.setLocation(request.getLocation());
        supplier.setAddress(request.getAddress());

        if (request.getActive() != null) {
            supplier.setActive(request.getActive());
        }
        log.info("Creating new supplier with name: {}", request.getSupplierName());
        return repository.save(supplier);
    }

    @Transactional
    public Supplier updateEntity(Long id, SupplierRequest request) {

        Supplier supplier = getEntityById(id);

        supplier.setSupplierName(request.getSupplierName());
        supplier.setContact(request.getContact());
        supplier.setLocation(request.getLocation());
        supplier.setAddress(request.getAddress());

        if (request.getActive() != null) {
            supplier.setActive(request.getActive());
        }
        log.info("Updating supplier with ID: {}", supplier.getId());
        return repository.save(supplier);
    }

    @Transactional
    public void deactivate(Long id) {

        Supplier supplier = getEntityById(id);
        log.info("Deactivating supplier with ID: {}", supplier.getId());
        supplier.setActive(false);

        repository.save(supplier);
    }

    // -----------------------------
    // DTO returning methods for controllers
    // -----------------------------

    @Transactional(readOnly = true)
    public java.util.List<SupplierResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(EntityDtoMapper::toSupplierResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SupplierResponse getById(Long id) {
        Supplier s = getEntityById(id);
        return EntityDtoMapper.toSupplierResponse(s);
    }

    @Transactional
    public SupplierResponse create(SupplierRequest request) {
        Supplier s = createEntity(request);
        return EntityDtoMapper.toSupplierResponse(s);
    }

    @Transactional
    public SupplierResponse update(Long id, SupplierRequest request) {
        Supplier s = updateEntity(id, request);
        return EntityDtoMapper.toSupplierResponse(s);
    }
}
