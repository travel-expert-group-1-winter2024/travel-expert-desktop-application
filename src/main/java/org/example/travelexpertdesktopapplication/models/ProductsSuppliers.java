package org.example.travelexpertdesktopapplication.models;

public class ProductsSuppliers {
    private int productSupplierId;
    private int productId;
    private int supplierId;

    private Product product;
    private Supplier supplier;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "ProductsSuppliers{" +
                "productSupplierId=" + productSupplierId +
                ", productId=" + productId +
                ", supplierId=" + supplierId +
                '}';
    }
}
