package com.ecommerce.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wishlist")
public class Wishlist {
    @Id
    private String id;
    private String userId;
    private String productId;
    private String productName;
    private double price;
    private String url;
  

    
    

    public Wishlist() {
    }

    public Wishlist(String id, String userId, String productId, String productName, double price, String url) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.url = url;
    }

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
      public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

   
}
