package com.ecommerce.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.repository.ProductRepository;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

   public Product getProductById(String productId) {
    return productRepository.findById(productId).orElse(null);
}

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String productId, Product updatedProduct) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setQuantity(updatedProduct.getQuantity());
            return productRepository.save(existingProduct);
        }

        return null;
    }

    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }
}
