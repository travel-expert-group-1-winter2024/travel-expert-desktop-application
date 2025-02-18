package org.example.travelexpertdesktopapplication.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Supplier {
    private SimpleIntegerProperty suppliercontactid;
    private SimpleStringProperty supconfirstname;
    private SimpleStringProperty supconlastname;
    private SimpleStringProperty supconcompany;
    private SimpleStringProperty supconaddress;
    private SimpleStringProperty supconcity;
    private SimpleStringProperty supconprov;
    private SimpleStringProperty supconpostal;
    private SimpleStringProperty supconbusphone;
    private SimpleStringProperty supconfax;
    private SimpleStringProperty supconcountry;
    private SimpleStringProperty supconemail;
    private SimpleStringProperty supconurl;
    private SimpleStringProperty affiliationid;
    private SimpleIntegerProperty supplierid;

    public Supplier(SimpleIntegerProperty suppliercontactid, SimpleStringProperty supconfirstname, SimpleStringProperty supconlastname,
                    SimpleStringProperty supconcompany, SimpleStringProperty supconcity, SimpleStringProperty supconprov,
                    SimpleStringProperty supconpostal, SimpleStringProperty supconbusphone, SimpleStringProperty supconfax,
                    SimpleStringProperty supconcountry, SimpleStringProperty supconemail, SimpleStringProperty supconurl,
                    SimpleStringProperty affiliationid, SimpleIntegerProperty supplierid,SimpleStringProperty supconaddress) {
        this.suppliercontactid = suppliercontactid;
        this.supconfirstname = supconfirstname;
        this.supconlastname = supconlastname;
        this.supconcompany = supconcompany;
        this.supconcity = supconcity;
        this.supconprov = supconprov;
        this.supconpostal = supconpostal;
        this.supconbusphone = supconbusphone;
        this.supconfax = supconfax;
        this.supconcountry = supconcountry;
        this.supconemail = supconemail;
        this.supconurl = supconurl;
        this.affiliationid = affiliationid;
        this.supplierid = supplierid;
        this.supconaddress = supconaddress;
    }

    public int getSupplierid() {
        return supplierid.get();
    }

    public SimpleIntegerProperty supplieridProperty() {
        return supplierid;
    }

    public String getAffiliationid() {
        return affiliationid.get();
    }

    public SimpleStringProperty affiliationidProperty() {
        return affiliationid;
    }

    public String getSupconurl() {
        return supconurl.get();
    }

    public SimpleStringProperty supconurlProperty() {
        return supconurl;
    }

    public String getSupconemail() {
        return supconemail.get();
    }

    public SimpleStringProperty supconemailProperty() {
        return supconemail;
    }

    public String getSupconcountry() {
        return supconcountry.get();
    }

    public SimpleStringProperty supconcountryProperty() {
        return supconcountry;
    }

    public String getSupconfax() {
        return supconfax.get();
    }

    public SimpleStringProperty supconfaxProperty() {
        return supconfax;
    }

    public String getSupconbusphone() {
        return supconbusphone.get();
    }

    public SimpleStringProperty supconbusphoneProperty() {
        return supconbusphone;
    }

    public String getSupconpostal() {
        return supconpostal.get();
    }

    public SimpleStringProperty supconpostalProperty() {
        return supconpostal;
    }

    public String getSupconprov() {
        return supconprov.get();
    }

    public SimpleStringProperty supconprovProperty() {
        return supconprov;
    }

    public String getSupconcity() {
        return supconcity.get();
    }

    public SimpleStringProperty supconcityProperty() {
        return supconcity;
    }

    public String getSupconcompany() {
        return supconcompany.get();
    }

    public SimpleStringProperty supconcompanyProperty() {
        return supconcompany;
    }

    public String getSupconlastname() {
        return supconlastname.get();
    }

    public SimpleStringProperty supconlastnameProperty() {
        return supconlastname;
    }

    public String getSupconfirstname() {
        return supconfirstname.get();
    }

    public SimpleStringProperty supconfirstnameProperty() {
        return supconfirstname;
    }

    public int getSuppliercontactid() {
        return suppliercontactid.get();
    }

    public SimpleIntegerProperty suppliercontactidProperty() {
        return suppliercontactid;
    }

    public String getSupconaddress(){
        return supconaddress.get();
    }

}
