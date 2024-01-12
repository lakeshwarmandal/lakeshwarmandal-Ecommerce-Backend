package com.ecommerce.demo.controller;

import com.ecommerce.demo.model.Wishlist;
import com.ecommerce.demo.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistController(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @GetMapping
    public ResponseEntity<List<Wishlist>> getAllWishlistItems() {
        List<Wishlist> wishlistItems = wishlistRepository.findAll();
        return new ResponseEntity<>(wishlistItems, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Wishlist>> getWishlistItemsByUserId(@PathVariable("userId") String userId) {
    List<Wishlist> wishlistItems = wishlistRepository.findByUserId(userId);
    if (wishlistItems.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(wishlistItems, HttpStatus.OK);
}


    @PostMapping
public ResponseEntity<Wishlist> addWishlistItem(@RequestBody Wishlist wishlistItem) {
    String productId = wishlistItem.getProductId();
    Optional<Wishlist> existingItem = wishlistRepository.findByProductId(productId);

    if (existingItem.isPresent()) {
        // Wishlist item already exists for the product
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    Wishlist addedWishlistItem = wishlistRepository.save(wishlistItem);
    return new ResponseEntity<>(addedWishlistItem, HttpStatus.CREATED);
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlistItem(@PathVariable("id") String id) {
        Optional<Wishlist> wishlistItem = wishlistRepository.findById(id);
        if (wishlistItem.isPresent()) {
            wishlistRepository.delete(wishlistItem.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
