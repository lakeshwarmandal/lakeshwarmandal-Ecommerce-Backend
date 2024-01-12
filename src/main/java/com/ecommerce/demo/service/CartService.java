package com.ecommerce.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.demo.model.Cart;
import com.ecommerce.demo.repository.CartRepository;

@Service
public class CartService {
    
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
    
    public Optional<Cart> getCartById(String id) {
        return cartRepository.findById(id);
    }
    
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }
    
    public Cart updateCart(Cart cart) {
        return cartRepository.save(cart);
    }
    
    public boolean deleteCart(String id) {
    Optional<Cart> cartOptional = cartRepository.findById(id);
    if (cartOptional.isPresent()) {
        cartRepository.deleteById(id);
        return true; // Deletion successful
    } else {
        return false; // Cart not found
    }
}

    
    public Optional<Cart> getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }
    
    public void deleteCartByUserId(String userId) {
        cartRepository.deleteByUserId(userId);
    }

    
}
