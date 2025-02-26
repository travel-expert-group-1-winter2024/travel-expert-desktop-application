package org.example.travelexpertdesktopapplication.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private SimpleIntegerProperty customerid;
    private SimpleStringProperty custfirstname;
    private SimpleStringProperty custlastname;
    private SimpleStringProperty custaddress;
    private SimpleStringProperty custcity;
    private SimpleStringProperty custprov;
    private SimpleStringProperty custpostal;
    private SimpleStringProperty custcountry;
    private SimpleStringProperty custhomephone;
    private SimpleStringProperty custbusphone;
    private SimpleStringProperty custemail;
    private SimpleIntegerProperty agentid;

    public Customer(SimpleIntegerProperty customerid, SimpleStringProperty custfirstname, SimpleStringProperty custlastname,
                    SimpleStringProperty custaddress, SimpleStringProperty custcity, SimpleStringProperty custprov,
                    SimpleStringProperty custpostal, SimpleStringProperty custcountry, SimpleStringProperty custhomephone,
                    SimpleStringProperty custbusphone, SimpleStringProperty custemail, SimpleIntegerProperty agentid) {
        this.customerid = customerid;
        this.custfirstname = custfirstname;
        this.custlastname = custlastname;
        this.custaddress = custaddress;
        this.custcity = custcity;
        this.custprov = custprov;
        this.custpostal = custpostal;
        this.custcountry = custcountry;
        this.custhomephone = custhomephone;
        this.custbusphone = custbusphone;
        this.custemail = custemail;
        this.agentid = agentid;
    }

    public int getCustomerid() {
        return customerid.get();
    }

    public SimpleIntegerProperty customeridProperty() {
        return customerid;
    }

    public String getCustfirstname() {
        return custfirstname.get();
    }

    public SimpleStringProperty custfirstnameProperty() {
        return custfirstname;
    }

    public String getCustlastname() {
        return custlastname.get();
    }

    public SimpleStringProperty custlastnameProperty() {
        return custlastname;
    }

    public String getCustaddress() {
        return custaddress.get();
    }

    public SimpleStringProperty custaddressProperty() {
        return custaddress;
    }

    public String getCustcity() {
        return custcity.get();
    }

    public SimpleStringProperty custcityProperty() {
        return custcity;
    }

    public String getCustprov() {
        return custprov.get();
    }

    public SimpleStringProperty custprovProperty() {
        return custprov;
    }

    public String getCustpostal() {
        return custpostal.get();
    }

    public SimpleStringProperty custpostalProperty() {
        return custpostal;
    }

    public String getCustcountry() {
        return custcountry.get();
    }

    public SimpleStringProperty custcountryProperty() {
        return custcountry;
    }

    public String getCusthomephone() {
        return custhomephone.get();
    }

    public SimpleStringProperty custhomephoneProperty() {
        return custhomephone;
    }

    public String getCustbusphone() {
        return custbusphone.get();
    }

    public SimpleStringProperty custbusphoneProperty() {
        return custbusphone;
    }

    public String getCustemail() {
        return custemail.get();
    }

    public SimpleStringProperty custemailProperty() {
        return custemail;
    }

    public int getAgentid() {
        return agentid.get();
    }

    public SimpleIntegerProperty agentidProperty() {
        return agentid;
    }
}
