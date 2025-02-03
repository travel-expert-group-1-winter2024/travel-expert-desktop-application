module org.example.travelexpertdesktopapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.travelexpertdesktopapplication to javafx.fxml;
    exports org.example.travelexpertdesktopapplication;
}