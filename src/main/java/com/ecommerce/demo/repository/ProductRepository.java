package com.ecommerce.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.ecommerce.demo.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    
}
