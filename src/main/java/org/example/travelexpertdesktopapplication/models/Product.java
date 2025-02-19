package org.example.travelexpertdesktopapplication.models;

public class Product {
    private int productId;
    private String productName;

    // Constructor
    public Product(int productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    // Getter and Setter for productId
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    // Getter and Setter for productName
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Override toString() for better display in TableView
    @Override
    public String toString() {
        return productName;
    }
}

