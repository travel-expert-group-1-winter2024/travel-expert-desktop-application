module org.example.travelexpertdesktopapplication {
    requires java.sql;
    requires com.jfoenix;
    requires org.tinylog.api;
    requires spring.messaging;
    requires spring.websocket;
    requires com.fasterxml.jackson.annotation;
    requires org.postgresql.jdbc;
    requires spring.context;
    requires MaterialFX;
    requires java.desktop;
    requires eu.hansolo.tilesfx;
    requires javafx.controls;
    requires spring.security.crypto;


    opens org.example.travelexpertdesktopapplication.models to
            com.fasterxml.jackson.databind,
            spring.core,
            spring.messaging;
    exports org.example.travelexpertdesktopapplication.models;
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