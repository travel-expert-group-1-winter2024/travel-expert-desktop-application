package org.example.travelexpertdesktopapplication.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Agent {
    private final SimpleIntegerProperty agentID;
    private final SimpleStringProperty agtFirstName;
    private final SimpleStringProperty agtMiddleInitial;
    private final SimpleStringProperty agtLastName;
    private final SimpleStringProperty agtBusPhone;
    private final SimpleStringProperty agtEmail;
    private final SimpleStringProperty agtPosition;
    private final SimpleIntegerProperty agencyid;

    // Constructor
    public Agent(int agentID, String agtFirstName, String agtMiddleInitial, String agtLastName,
                 String agtBusPhone, String agtEmail, String agtPosition, int agencyId) {
        this.agentID = new SimpleIntegerProperty(agentID);
        this.agtFirstName = new SimpleStringProperty(agtFirstName);
        this.agtMiddleInitial = new SimpleStringProperty(agtMiddleInitial);
        this.agtLastName = new SimpleStringProperty(agtLastName);
        this.agtBusPhone = new SimpleStringProperty(agtBusPhone);
        this.agtEmail = new SimpleStringProperty(agtEmail);
        this.agtPosition = new SimpleStringProperty(agtPosition);
        this.agencyid = new SimpleIntegerProperty(agencyId);
    }

    // Getters for properties (used by JavaFX TableView)
    public SimpleIntegerProperty agentIDProperty() { return agentID; }
    public SimpleStringProperty agtFirstNameProperty() { return agtFirstName; }
    public SimpleStringProperty agtMiddleInitialProperty() { return agtMiddleInitial; }
    public SimpleStringProperty agtLastNameProperty() { return agtLastName; }
    public SimpleStringProperty agtBusPhoneProperty() { return agtBusPhone; }
    public SimpleStringProperty agtEmailProperty() { return agtEmail; }
    public SimpleStringProperty agtPositionProperty() { return agtPosition; }
    public SimpleIntegerProperty agencyidProperty() { return agencyid; }

    // Regular getters and setters
    public int getAgentID() { return agentID.get(); }
    public void setAgentID(int agentID) { this.agentID.set(agentID); }

    public String getAgtFirstName() { return agtFirstName.get(); }
    public void setAgtFirstName(String agtFirstName) { this.agtFirstName.set(agtFirstName); }

    public String getAgtMiddleInitial() { return agtMiddleInitial.get(); }
    public void setAgtMiddleInitial(String agtMiddleInitial) { this.agtMiddleInitial.set(agtMiddleInitial); }

    public String getAgtLastName() { return agtLastName.get(); }
    public void setAgtLastName(String agtLastName) { this.agtLastName.set(agtLastName); }

    public String getAgtBusPhone() { return agtBusPhone.get(); }
    public void setAgtBusPhone(String agtBusPhone) { this.agtBusPhone.set(agtBusPhone); }

    public String getAgtEmail() { return agtEmail.get(); }
    public void setAgtEmail(String agtEmail) { this.agtEmail.set(agtEmail); }

    public String getAgtPosition() { return agtPosition.get(); }
    public void setAgtPosition(String agtPosition) { this.agtPosition.set(agtPosition); }

    public int getAgencyId() { return agencyid.get(); }
    public void setAgencyId(int agencyId) { this.agencyid.set(agencyId); }
}

