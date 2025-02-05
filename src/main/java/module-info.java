module org.example.travelexpertdesktopapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.travelexpertdesktopapplication to javafx.fxml;
    exports org.example.travelexpertdesktopapplication;
}