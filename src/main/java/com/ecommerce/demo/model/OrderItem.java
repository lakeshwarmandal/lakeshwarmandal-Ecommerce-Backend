package com.ecommerce.demo.model;

public class OrderItem {
    private String productId;
    private int quantity;
    private double price;
    private String productName;
    private String url;

    public OrderItem() {
    }

    public OrderItem(String productId, int quantity, double price, String productName, String url) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.productName = productName;
        this.url = url;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
