package org.example.travelexpertdesktopapplication.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Supplier {
    private SimpleIntegerProperty supplierid;
    private SimpleStringProperty supname;

    public Supplier(SimpleIntegerProperty supplierid, SimpleStringProperty supname) {
        this.supplierid = supplierid;
        this.supname = supname;
    }

    public int getSupplierid() {
        return supplierid.get();
    }

    public SimpleIntegerProperty supplieridProperty() {
        return supplierid;
    }

    public void setSupplierid(int supplierid) {
        this.supplierid.set(supplierid);
    }

    public String getSupname() {
        return supname.get();
    }

    public SimpleStringProperty supnameProperty() {
        return supname;
    }

    public void setSupname(String supname) {
        this.supname.set(supname);
    }
}
