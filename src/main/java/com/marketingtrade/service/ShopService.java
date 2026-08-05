package com.marketingtrade.service;

import com.marketingtrade.dto.ShopRequest;
import com.marketingtrade.entity.Shop;
import com.marketingtrade.exception.ResourceNotFoundException;
import com.marketingtrade.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShopService {

    private final ShopRepository repository;

    public ShopService(ShopRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Shop> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Shop getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Shop not found: " + id));
    }

    public Shop create(ShopRequest request) {

        Shop shop = new Shop();

        shop.setShopName(request.shopName());
        shop.setOwnerName(request.ownerName());
        shop.setContactNumber(request.contactNumber());
        shop.setAddress(request.address());
        shop.setLocation(request.location());

        if (request.active() != null) {
            shop.setActive(request.active());
        }

        return repository.save(shop);
    }

    public Shop update(Long id, ShopRequest request) {

        Shop shop = getById(id);

        shop.setShopName(request.shopName());
        shop.setOwnerName(request.ownerName());
        shop.setContactNumber(request.contactNumber());
        shop.setAddress(request.address());
        shop.setLocation(request.location());

        if (request.active() != null) {
            shop.setActive(request.active());
        }

        return repository.save(shop);
    }

    public void deactivate(Long id) {

        Shop shop = getById(id);
        shop.setActive(false);

        repository.save(shop);
    }
}