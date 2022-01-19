package com.shopservice.shopservice.api;

import com.shopservice.shopservice.repo.model.Shop;
import com.shopservice.shopservice.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public final class ShopController {
    private final ShopService shopService;

    @GetMapping
    public ResponseEntity<List<com.shopservice.shopservice.repo.model.Shop>> index() {
        final List<com.shopservice.shopservice.repo.model.Shop> items = shopService.fetchAll();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.shopservice.shopservice.repo.model.Shop> showById(@PathVariable long id) {
        try {
            final com.shopservice.shopservice.repo.model.Shop item = shopService.fetchItemById(id);
            return ResponseEntity.ok(item);
        }
        catch (IllegalArgumentException error){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.shopservice.shopservice.api.dto.Shop item) {
        final String name = item.getName();
        final int price = item.getPrice();
        final long itemId = shopService.create(name, price);
        final String itemUri = String.format("/items/%d", itemId);

        return ResponseEntity.created(URI.create(itemUri)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.shopservice.shopservice.api.dto.Shop item) {
        final String name = item.getName();
        final int price = item.getPrice();
        try {
            shopService.update(id, name, price);
            return ResponseEntity.noContent().build();
        }
        catch (IllegalArgumentException error){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemById(@PathVariable long id) {
        shopService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
