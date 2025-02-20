package org.example.travelexpertdesktopapplication.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Agency {
    private final SimpleIntegerProperty agencyID;
    private final SimpleStringProperty agncyAddress;
    private final SimpleStringProperty agncyCity;
    private final SimpleStringProperty agncyProv;
    private final SimpleStringProperty agncyPostal;
    private final SimpleStringProperty agncyCountry;
    private final SimpleStringProperty agncyPhone;
    private final SimpleStringProperty agncyFax;

    // Constructor
    public Agency(int agencyID, String agncyAddress, String agncyCity, String agncyProv, String agncyPostal, String agncyCountry, String agncyPhone, String agncyFax) {
        this.agencyID = new SimpleIntegerProperty(agencyID);
        this.agncyAddress = new SimpleStringProperty(agncyAddress);
        this.agncyCity = new SimpleStringProperty(agncyCity);
        this.agncyProv = new SimpleStringProperty(agncyProv);
        this.agncyPostal = new SimpleStringProperty(agncyPostal);
        this.agncyCountry = new SimpleStringProperty(agncyCountry);
        this.agncyPhone = new SimpleStringProperty(agncyPhone);
        this.agncyFax = new SimpleStringProperty(agncyFax);
    }

    // Getters for properties (used by JavaFX TableView)
    public SimpleIntegerProperty agencyIDProperty() {
        return agencyID;
    }

    public SimpleStringProperty agncyAddressProperty() {
        return agncyAddress;
    }

    public SimpleStringProperty agncyCityProperty() {
        return agncyCity;
    }

    public SimpleStringProperty agncyProvProperty() {
        return agncyProv;
    }

    public SimpleStringProperty agncyPostalProperty() {
        return agncyPostal;
    }

    public SimpleStringProperty agncyCountryProperty() {
        return agncyCountry;
    }

    public SimpleStringProperty agncyPhoneProperty() {
        return agncyPhone;
    }

    public SimpleStringProperty agncyFaxProperty() {
        return agncyFax;
    }

    // Regular getters and setters
    public int getAgencyID() {
        return agencyID.get();
    }

    public void setAgencyID(int agencyID) {
        this.agencyID.set(agencyID);
    }

    public String getAgncyAddress() {
        return agncyAddress.get();
    }

    public void setAgncyAddress(String agncyAddress) {
        this.agncyAddress.set(agncyAddress);
    }

    public String getAgncyCity() {
        return agncyCity.get();
    }

    public void setAgncyCity(String agncyCity) {
        this.agncyCity.set(agncyCity);
    }

    public String getAgncyProv() {
        return agncyProv.get();
    }

    public void setAgncyProv(String agncyProv) {
        this.agncyProv.set(agncyProv);
    }

    public String getAgncyPostal() {
        return agncyPostal.get();
    }

    public void setAgncyPostal(String agncyPostal) {
        this.agncyPostal.set(agncyPostal);
    }

    public String getAgncyCountry() {
        return agncyCountry.get();
    }

    public void setAgncyCountry(String agncyCountry) {
        this.agncyCountry.set(agncyCountry);
    }

    public String getAgncyPhone() {
        return agncyPhone.get();
    }

    public void setAgncyPhone(String agncyPhone) {
        this.agncyPhone.set(agncyPhone);
    }

    public String getAgncyFax() {
        return agncyFax.get();
    }

    public void setAgncyFax(String agncyFax) {
        this.agncyFax.set(agncyFax);
    }
}