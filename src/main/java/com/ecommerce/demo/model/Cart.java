package com.ecommerce.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart")
public class Cart {
    @Id
    private String id;
    private String userId; 
    private List<CartItem> cartItems; // Collection of cart items

    public Cart(String userId) {
        this.userId = userId;
        this.cartItems = new ArrayList<>();
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
    public int getTotalItems() {
        int totalItems = 0;
        for (CartItem cartItem : cartItems) {
            totalItems += cartItem.getQuantity();
        }
        return totalItems;
    }
}
