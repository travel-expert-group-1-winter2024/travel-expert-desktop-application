module org.example.travelexpertdesktopapplication {
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires javafx.controls;


    opens org.example.travelexpertdesktopapplication to javafx.fxml;
    exports org.example.travelexpertdesktopapplication;
    exports org.example.travelexpertdesktopapplication.services;
    opens org.example.travelexpertdesktopapplication.services to javafx.fxml;
    exports org.example.travelexpertdesktopapplication.controllers;
    opens org.example.travelexpertdesktopapplication.controllers to javafx.fxml;
    exports org.example.travelexpertdesktopapplication.dao;
    opens org.example.travelexpertdesktopapplication.dao to javafx.fxml;
    exports org.example.travelexpertdesktopapplication.auth;
    opens org.example.travelexpertdesktopapplication.auth to javafx.fxml;
}