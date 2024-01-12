package com.ecommerce.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.ecommerce.demo.model.Wishlist;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, String> {

    List<Wishlist> findByUserId(String userId);

    Optional<Wishlist> findByProductId(String productId);
    
}
