package org.example.travelexpertdesktopapplication.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class BookingDetails {
    //* Props
    private SimpleIntegerProperty bookingdetailid;
    private SimpleDoubleProperty itenearyno;
    private SimpleObjectProperty<LocalDateTime> tripstart;
    private SimpleObjectProperty<LocalDateTime> tripend;
    private SimpleStringProperty description;
    private SimpleStringProperty destination;
    private SimpleDoubleProperty baseprice;
    private SimpleDoubleProperty agencycommission;
    private SimpleIntegerProperty bookingid;
    private SimpleStringProperty regionid;
    private SimpleStringProperty classid;
    private SimpleStringProperty feeid;
    private SimpleIntegerProperty productsupplierid;

    //* Constructor
    public BookingDetails(int bookingdetailid,
                          double itenearyno,
                          Object tripstart,
                          Object tripend,
                          String description,
                          String destination,
                          double baseprice,
                          double agencycommission,
                          int bookingid,
                          String regionid,
                          String classid,
                          String feeid,
                          int productsupplierid)
    {
        this.bookingdetailid = new SimpleIntegerProperty(bookingdetailid);
        this.itenearyno = new SimpleDoubleProperty(itenearyno);
        this.tripstart = new SimpleObjectProperty<>((LocalDateTime)tripstart);
        this.tripend = new SimpleObjectProperty<>((LocalDateTime) tripend);
        this.description = new SimpleStringProperty(description);
        this.destination = new SimpleStringProperty(destination);
        this.baseprice = new SimpleDoubleProperty(baseprice);
        this.agencycommission = new SimpleDoubleProperty(agencycommission);
        this.bookingid = new SimpleIntegerProperty(bookingid);
        this.regionid = new SimpleStringProperty(regionid);
        this.classid = new SimpleStringProperty(classid);
        this.feeid = new SimpleStringProperty(feeid);
        this.productsupplierid = new SimpleIntegerProperty(productsupplierid);
    }

    //* Getters & Setters
    public int getBookingdetailid() {
        return bookingdetailid.get();
    }

    public SimpleIntegerProperty bookingdetailidProperty() {
        return bookingdetailid;
    }

    public void setBookingdetailid(int bookingdetailid) {
        this.bookingdetailid.set(bookingdetailid);
    }

    public double getItenearyno() {
        return itenearyno.get();
    }

    public SimpleDoubleProperty itenearynoProperty() {
        return itenearyno;
    }

    public void setItenearyno(double itenearyno) {
        this.itenearyno.set(itenearyno);
    }

    public LocalDateTime getTripstart() {
        return tripstart.get();
    }

    public SimpleObjectProperty<LocalDateTime> tripstartProperty() {
        return tripstart;
    }

    public void setTripstart(LocalDateTime tripstart) {
        this.tripstart.set(tripstart);
    }

    public LocalDateTime getTripend() {
        return tripend.get();
    }

    public SimpleObjectProperty<LocalDateTime> tripendProperty() {
        return tripend;
    }

    public void setTripend(LocalDateTime tripend) {
        this.tripend.set(tripend);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getDestination() {
        return destination.get();
    }

    public SimpleStringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public double getBaseprice() {
        return baseprice.get();
    }

    public SimpleDoubleProperty basepriceProperty() {
        return baseprice;
    }

    public void setBaseprice(double baseprice) {
        this.baseprice.set(baseprice);
    }

    public double getAgencycommission() {
        return agencycommission.get();
    }

    public SimpleDoubleProperty agencycommissionProperty() {
        return agencycommission;
    }

    public void setAgencycommission(double agencycommission) {
        this.agencycommission.set(agencycommission);
    }

    public int getBookingid() {
        return bookingid.get();
    }

    public SimpleIntegerProperty bookingidProperty() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid.set(bookingid);
    }

    public String getRegionid() {
        return regionid.get();
    }

    public SimpleStringProperty regionidProperty() {
        return regionid;
    }

    public void setRegionid(String regionid) {
        this.regionid.set(regionid);
    }

    public String getClassid() {
        return classid.get();
    }

    public SimpleStringProperty classidProperty() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid.set(classid);
    }

    public String getFeeid() {
        return feeid.get();
    }

    public SimpleStringProperty feeidProperty() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid.set(feeid);
    }

    public int getProductsupplierid() {
        return productsupplierid.get();
    }

    public SimpleIntegerProperty productsupplieridProperty() {
        return productsupplierid;
    }

    public void setProductsupplierid(int productsupplierid) {
        this.productsupplierid.set(productsupplierid);
    }
}//!Class
