package com.marketingtrade.service;

import com.marketingtrade.dto.ShopRequest;
import com.marketingtrade.dto.ShopResponse;
import com.marketingtrade.entity.Shop;
import com.marketingtrade.exception.ResourceNotFoundException;
import com.marketingtrade.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ShopService {

    private final ShopRepository repository;

    public ShopService(ShopRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public java.util.List<Shop> getAllEntities() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Shop getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Shop not found: " + id));
    }

    public Shop createEntity(ShopRequest request) {

        Shop shop = new Shop();

        shop.setShopName(request.getShopName());
        shop.setOwnerName(request.getOwnerName());
        shop.setContactNumber(request.getContactNumber());
        shop.setAddress(request.getAddress());
        shop.setLocation(request.getLocation());

        if (request.getActive() != null) {
            shop.setActive(request.getActive());
        }
        log.info("Creating new shop with name: {}", request.getShopName());
        return repository.save(shop);
    }

    public Shop updateEntity(Long id, ShopRequest request) {

        Shop shop = getEntityById(id);
        log.info("Updating shop with id: {}", id);

        shop.setShopName(request.getShopName());
        shop.setOwnerName(request.getOwnerName());
        shop.setContactNumber(request.getContactNumber());
        shop.setAddress(request.getAddress());
        shop.setLocation(request.getLocation());

        if (request.getActive() != null) {
            shop.setActive(request.getActive());
        }
        log.info("Updating shop with id: {}", id);
        return repository.save(shop);
    }

    public void deactivate(Long id) {
        log.info("Deactivating shop with id: {}", id);

        Shop shop = getEntityById(id);
        shop.setActive(false);

        repository.save(shop);
    }

    // -----------------------------
    // DTO returning methods for controllers
    // -----------------------------

    @Transactional(readOnly = true)
    public java.util.List<ShopResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(EntityDtoMapper::toShopResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ShopResponse getById(Long id) {
        Shop s = getEntityById(id);
        return EntityDtoMapper.toShopResponse(s);
    }

    @Transactional
    public ShopResponse create(ShopRequest request) {
        Shop s = createEntity(request);
        return EntityDtoMapper.toShopResponse(s);
    }

    @Transactional
    public ShopResponse update(Long id, ShopRequest request) {
        Shop s = updateEntity(id, request);
        return EntityDtoMapper.toShopResponse(s);
    }
}

