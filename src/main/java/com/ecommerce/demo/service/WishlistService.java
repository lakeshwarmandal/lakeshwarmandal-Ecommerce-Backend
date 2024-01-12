package com.ecommerce.demo.service;

import com.ecommerce.demo.model.Wishlist;
import com.ecommerce.demo.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> getAllWishlistItems() {
        return wishlistRepository.findAll();
    }

    public Optional<Wishlist> getWishlistItemById(String id) {
        return wishlistRepository.findById(id);
    }

    public Wishlist addWishlistItem(Wishlist wishlistItem) {
        return wishlistRepository.save(wishlistItem);
    }

    public void deleteWishlistItem(String id) {
        Optional<Wishlist> wishlistItem = wishlistRepository.findById(id);
        wishlistItem.ifPresent(item -> wishlistRepository.delete(item));
    }
}
