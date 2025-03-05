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





    //* Constructor
    public AgentDashboardKPI() {
        this.bookingid = new SimpleIntegerProperty();
        this.baseprice = new SimpleDoubleProperty();
        this.agencycommission = new SimpleDoubleProperty();
        this.customerid = new SimpleIntegerProperty();
        this.agentFirstName = new SimpleStringProperty();
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
        this.agencycommission.set(agencycommission);
    }

    @Override
    public String toString() {
        return "AgentDashboardKPI{" +
                "agentFirstName=" + agentFirstName +
                ", bookingid=" + bookingid +
                ", baseprice=" + baseprice +
                ", agencycommission=" + agencycommission +
                ", customerid=" + customerid +
                '}';
    }

}
