package com.ecommerce.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.demo.model.Cart;
import com.ecommerce.demo.model.CartItem;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.service.CartService;
import com.ecommerce.demo.service.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping 
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();

        if (carts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(carts);
        }
    }

    @GetMapping("/{userId}")
public ResponseEntity<Map<String, Object>> viewCart(@PathVariable String userId) {
    Optional<Cart> optionalCart = cartService.getCartByUserId(userId);

    if (optionalCart.isPresent()) {
        Cart userCart = optionalCart.get();
        int totalItems = userCart.getTotalItems();
        List<CartItem> cartItems = userCart.getCartItems();
        
        Map<String, Object> response = new HashMap<>();
        response.put("cartId", userCart.getId()); // Include the cart ID in the response
        response.put("totalItems", totalItems);
        response.put("items", cartItems);

        return ResponseEntity.ok(response);
    } else {
        return ResponseEntity.notFound().build();
    }
}


@PostMapping("/{userId}")
public ResponseEntity<Cart> addToCart(@PathVariable String userId, @RequestBody CartItem cartItem) {
    Optional<Cart> existingCart = cartService.getCartByUserId(userId);

    if (existingCart.isPresent()) {
        Cart userCart = existingCart.get();

        Optional<CartItem> existingCartItem = userCart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(cartItem.getProductId()))
                .findFirst();
        
        if (existingCartItem.isPresent()) {
            CartItem cartItemToUpdate = existingCartItem.get();
            int newQuantity = cartItemToUpdate.getQuantity() + cartItem.getQuantity();
            cartItemToUpdate.setQuantity(newQuantity);
        } else {
            // Fetch the product for the new item from the productService using the productId
            Product product = productService.getProductById(cartItem.getProductId());
            if (product == null) {
                return ResponseEntity.badRequest().build();
            }
            
            // Set the price, name, and URL of the product in the cart item
            cartItem.setPrice(product.getPrice());
            cartItem.setProductName(product.getName());
            cartItem.setUrl(product.getUrl());
            
            userCart.getCartItems().add(cartItem);
        }
        
        Cart updatedCart = cartService.updateCart(userCart);
        return ResponseEntity.ok(updatedCart);
    } else {
        // Fetch the product for the new item from the productService using the productId
        Product product = productService.getProductById(cartItem.getProductId());
        if (product == null) {
            return ResponseEntity.badRequest().build(); 
        }
        
        // Set the price, name, and URL of the product in the cart item
        cartItem.setPrice(product.getPrice());
        cartItem.setProductName(product.getName());
        cartItem.setUrl(product.getUrl());

        Cart newCart = new Cart(userId);
        newCart.getCartItems().add(cartItem);

        Cart createdCart = cartService.createCart(newCart);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCart);
    }
}



    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable String id, @RequestBody Cart cart) {
        Optional<Cart> existingCart = cartService.getCartById(id);
        if (existingCart.isPresent()) {
            cart.setId(id);
            Cart updatedCart = cartService.updateCart(cart);
            return ResponseEntity.ok(updatedCart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable String userId, @PathVariable String productId) {
        // Fetch the user's existing cart from the database
        Optional<Cart> existingCart = cartService.getCartByUserId(userId);

        if (existingCart.isPresent()) {
            Cart userCart = existingCart.get();

            // Find the cart item to delete based on the productId
            Optional<CartItem> cartItemToDelete = userCart.getCartItems().stream()
                    .filter(item -> item.getProductId().equals(productId))
                    .findFirst();

            if (cartItemToDelete.isPresent()) {
                // Remove the cart item from the cart
                userCart.getCartItems().remove(cartItemToDelete.get());

                // Save the updated cart using the cartService
                cartService.updateCart(userCart);

                return ResponseEntity.noContent().build();
            } else {
                // Cart item not found
                return ResponseEntity.notFound().build();
            }
        } else {
            // Cart not found
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
public ResponseEntity<Void> deleteCart(@PathVariable String id) {
    boolean deleted = cartService.deleteCart(id);
    if (deleted) {
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}

    
}
