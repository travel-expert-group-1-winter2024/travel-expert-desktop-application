package org.example.travelexpertdesktopapplication.models;

public class ProductsSuppliers {
    private int productSupplierId;
    private int productId;
    private int supplierId;

    public ProductsSuppliers(int productId, int supplierId) {
        this.productId = productId;
        this.supplierId = supplierId;
    }

    public ProductsSuppliers(int productSupplierId, int productId, int supplierId) {
        this.productSupplierId = productSupplierId;
        this.productId = productId;
        this.supplierId = supplierId;
    }

    public int getProductSupplierId() {
        return productSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        this.productSupplierId = productSupplierId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
}
