package com.shopservice.shopservice.service;

import com.shopservice.shopservice.repo.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.shopservice.shopservice.repo.model.Shop;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class ShopService {
    private final ShopRepo shopRepo;
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Shop> fetchAll() {
        return shopRepo.findAll();
    }

    public Shop fetchItemById(long id) throws IllegalArgumentException {
        final Optional<Shop> maybeItem = shopRepo.findById(id);

        if (maybeItem.isPresent())
            return maybeItem.get();
        else
            throw new IllegalArgumentException("Wrong id");
    }

    public long create(String name, int price){
        final Shop shop = new Shop(name, price);
        final Shop savedShop = shopRepo.save(shop);

        return savedShop.getId();
    }

    public void update(long id, String name, int price)
            throws IllegalArgumentException
    {
        final Optional<Shop> maybeItem = shopRepo.findById(id);

        if (maybeItem.isEmpty())
            throw new IllegalArgumentException("Wrong id");

        final Shop shop = maybeItem.get();
        if (name != null && !name.isBlank()) shop.setName(name);
        if (price != 0) shop.setPrice(price);
        shopRepo.save(shop);
    }

    public void deleteItem(long id){
        shopRepo.deleteById(id);
    }
}
