package org.example.travelexpertdesktopapplication.models;

public class PackagesProductsSuppliers {
    private int packageId;
    private int productSupplierId;

    public PackagesProductsSuppliers(int packageId, int productSupplierId) {
        this.packageId = packageId;
        this.productSupplierId = productSupplierId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getProductSupplierId() {
        return productSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        this.productSupplierId = productSupplierId;
    }
}
