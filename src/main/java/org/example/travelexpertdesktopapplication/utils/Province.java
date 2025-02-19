package org.example.travelexpertdesktopapplication.utils;

public enum Province {
    ALBERTA("Alberta", "AB"),
    BRITISH_COLUMBIA("British Columbia", "BC"),
    MANITOBA("Manitoba", "MB"),
    NEW_BRUNSWICK("New Brunswick", "NB"),
    NEWFOUNDLAND_AND_LABRADOR("Newfoundland and Labrador", "NL"),
    NOVA_SCOTIA("Nova Scotia", "NS"),
    ONTARIO("Ontario", "ON"),
    PRINCE_EDWARD_ISLAND("Prince Edward Island", "PE"),
    QUEBEC("Quebec", "QC"),
    SASKATCHEWAN("Saskatchewan", "SK"),
    NORTHWEST_TERRITORIES("Northwest Territories", "NT"),
    NUNAVUT("Nunavut", "NU"),
    YUKON("Yukon", "YT");

    private final String fullName;
    private final String abbreviation;

    Province(String fullName, String abbreviation) {
        this.fullName = fullName;
        this.abbreviation = abbreviation;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
