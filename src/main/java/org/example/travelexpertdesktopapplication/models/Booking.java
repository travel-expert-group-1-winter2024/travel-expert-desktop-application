package org.example.travelexpertdesktopapplication.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class Booking {
    //* Props
    private  SimpleIntegerProperty bookingid;
    private  SimpleObjectProperty<LocalDateTime> bookingdate;
    private  SimpleStringProperty bookingno;
    private  SimpleDoubleProperty travelercount;
    private  SimpleIntegerProperty customerid;
    private  SimpleStringProperty triptypeid;
    private  SimpleIntegerProperty packageid;

    //* Constructor
    public Booking(int bookingid,
                   Object bookingdate,
                   String bookingno,
                   Double travelercount,
                   int customerid,
                   String triptypeid,
                   int packageid)
    {
        this.bookingid = new SimpleIntegerProperty(bookingid);
        this.bookingdate = new SimpleObjectProperty<>((LocalDateTime) bookingdate);
        this.bookingno = new SimpleStringProperty(bookingno);
        this.travelercount = new SimpleDoubleProperty(travelercount);
        this.customerid = new SimpleIntegerProperty(customerid);
        this.triptypeid = new SimpleStringProperty(triptypeid);
        this.packageid = new SimpleIntegerProperty(packageid);
    }

    //* Getters and Setters
    public int getBookingid() {
        return bookingid.get();
    }

    public SimpleIntegerProperty bookingidProperty() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid.set(bookingid);
    }

    public LocalDateTime getBookingdate() {
        return bookingdate.get();
    }

    public SimpleObjectProperty<LocalDateTime> bookingdateProperty() {
        return bookingdate;
    }

    public void setBookingdate(LocalDateTime bookingdate) {
        this.bookingdate.set(bookingdate);
    }

    public String getBookingno() {
        return bookingno.get();
    }

    public SimpleStringProperty bookingnoProperty() {
        return bookingno;
    }

    public void setBookingno(String bookingno) {
        this.bookingno.set(bookingno);
    }

    public double getTravelercount() {
        return travelercount.get();
    }

    public SimpleDoubleProperty travelercountProperty() {
        return travelercount;
    }

    public void setTravelercount(double travelercount) {
        this.travelercount.set(travelercount);
    }

    public int getCustomerid() {
        return customerid.get();
    }

    public SimpleIntegerProperty customeridProperty() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid.set(customerid);
    }

    public String getTriptypeid() {
        return triptypeid.get();
    }

    public SimpleStringProperty triptypeidProperty() {
        return triptypeid;
    }

    public void setTriptypeid(String triptypeid) {
        this.triptypeid.set(triptypeid);
    }

    public int getPackageid() {
        return packageid.get();
    }

    public SimpleIntegerProperty packageidProperty() {
        return packageid;
    }

    public void setPackageid(int packageid) {
        this.packageid.set(packageid);
    }
}//!Class
