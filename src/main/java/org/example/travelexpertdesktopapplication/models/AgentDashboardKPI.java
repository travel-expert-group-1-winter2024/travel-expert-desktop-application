package org.example.travelexpertdesktopapplication.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AgentDashboardKPI {
    //* Props
    private SimpleStringProperty agentFirstName;
    private SimpleIntegerProperty bookingid;
    private SimpleDoubleProperty baseprice;
    private SimpleDoubleProperty agencycommission;
    private SimpleIntegerProperty customerid;
    private SimpleStringProperty destination;
    private SimpleDoubleProperty totalprice;
    private SimpleDoubleProperty totalAgencycommission;
    private SimpleIntegerProperty totalCustomers;





    //* Constructor
    public AgentDashboardKPI() {
        this.bookingid = new SimpleIntegerProperty();
        this.baseprice = new SimpleDoubleProperty();
        this.agencycommission = new SimpleDoubleProperty();
        this.customerid = new SimpleIntegerProperty();
        this.agentFirstName = new SimpleStringProperty();
        this.destination = new SimpleStringProperty();
        this.totalprice = new SimpleDoubleProperty();
        this.totalAgencycommission = new SimpleDoubleProperty();
        this.totalCustomers = new SimpleIntegerProperty();

    }


    public SimpleStringProperty agentFirstNameProperty() {
        return agentFirstName;
    }

    public String getAgentFirstName() {
        return agentFirstName.get();
    }

    public void setAgentFirstName(String agentFirstName) {
        this.agentFirstName.set(agentFirstName);
    }
    public int getBookingid() {
        return bookingid.get();
    }

    public SimpleIntegerProperty bookingidProperty() {
        return bookingid;
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

    public void setBookingid(int bookingid) {
        this.bookingid.set(bookingid);
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
        this.agencycommission.set(agencycommission);}

    public String getDestination() {
        return destination.get();
    }

    public SimpleStringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public double getTotalprice() {
        return totalprice.get();
    }

    public SimpleDoubleProperty totalpriceProperty() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice.set(totalprice);
    }

    public double getTotalAgencycommission() {
        return totalAgencycommission.get();
    }

    public SimpleDoubleProperty totalAgencycommissionProperty() {
        return totalAgencycommission;
    }

    public void setTotalAgencycommission(double totalAgencycommission) {
        this.totalAgencycommission.set(totalAgencycommission);
    }
//
//    @Override

    public int getTotalCustomers() {
        return totalCustomers.get();
    }

    public SimpleIntegerProperty totalCustomersProperty() {
        return totalCustomers;
    }

    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers.set(totalCustomers);
    }
//    public String toString() {
//        return "AgentDashboardKPI{" +
//                "\nagentFirstName=" + agentFirstName +
//                ", \nbookingid=" + bookingid +
//                ", \nbaseprice=" + baseprice +
//                ", \nagencycommission=" + agencycommission +
//                ", \ncustomerid=" + customerid +
//                ", \ndestination=" + destination +
//                '}';
//    }

}
