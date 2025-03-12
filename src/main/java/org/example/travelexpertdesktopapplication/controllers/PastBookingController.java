package org.example.travelexpertdesktopapplication.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.travelexpertdesktopapplication.auth.SessionManager;
import org.example.travelexpertdesktopapplication.dao.BookingDetailsDAO;
import org.example.travelexpertdesktopapplication.dao.SupplierDAO;
import org.example.travelexpertdesktopapplication.models.BookingDetails;
import org.example.travelexpertdesktopapplication.models.SupplierContacts;
import org.example.travelexpertdesktopapplication.utils.AlertBox;

public class PastBookingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<BookingDetails, Number> colAgencyCommission;

    @FXML
    private TableColumn<BookingDetails, Integer> colAgentID;

    @FXML
    private TableColumn<BookingDetails, Number> colBasePrice;

    @FXML
    private TableColumn<BookingDetails, Integer> colBookingID;

    @FXML
    private TableColumn<BookingDetails, String> colClass;

    @FXML
    private TableColumn<BookingDetails, String> colDescription;

    @FXML
    private TableColumn<BookingDetails, String> colDesctination;

    @FXML
    private TableColumn<BookingDetails, LocalDate> colEndDate;

    @FXML
    private TableColumn<BookingDetails, String> colFees;

    @FXML
    private TableColumn<BookingDetails, String> colProduct;

    @FXML
    private TableColumn<BookingDetails, String> colRegion;

    @FXML
    private TableColumn<BookingDetails, String> colSupplier;

    @FXML
    private TableColumn<BookingDetails, Integer> colbookingdetailID;

    @FXML
    private TableColumn<BookingDetails, LocalDate> colstartDate;

    @FXML
    private TableView<BookingDetails> lvPastBooking;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnReset;

    private ObservableList<BookingDetails> pastBookingsList = FXCollections.observableArrayList();
    private FilteredList<BookingDetails> filteredData;
    private final SessionManager sessionManager = SessionManager.getInstance();
    private int agentId;

    @FXML
    void initialize() {
        assert colAgencyCommission != null : "fx:id=\"colAgencyCommission\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert colAgentID != null : "fx:id=\"colAgentID\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert colBasePrice != null : "fx:id=\"colBasePrice\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert colBookingID != null : "fx:id=\"colBookingID\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert colClass != null : "fx:id=\"colClass\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert colDescription != null : "fx:id=\"colDescription\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert colDesctination != null : "fx:id=\"colDesctination\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert colEndDate != null : "fx:id=\"colEndDate\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert colFees != null : "fx:id=\"colFees\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert colProduct != null : "fx:id=\"colProduct\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert colRegion != null : "fx:id=\"colRegion\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert colSupplier != null : "fx:id=\"colSupplier\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert colbookingdetailID != null : "fx:id=\"colbookingdetailID\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert colstartDate != null : "fx:id=\"colstartDate\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert lvPastBooking != null : "fx:id=\"lvPastBooking\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert txtSearch != null : "fx:id=\"txtSearch\" was not injected: check your FXML file 'past-booking-view.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'past-booking-view.fxml'.";

        agentId = sessionManager.getUser().getAgentId();

        setupBookingTable();
        displayAllPastBookings();
        filteredData = new FilteredList<>(pastBookingsList, p -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(bookingDetails -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true; // Show all if search is empty
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // Call the method on the instance to convert its data into a searchable string
                return bookingDetails.toSearchableStringPastBookings().contains(lowerCaseFilter);
            });
        });
        SortedList<BookingDetails> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(lvPastBooking.comparatorProperty());
        lvPastBooking.setItems(sortedData);
    }

    /**
     * Setup data for each column and bind them from the database columns
     */
    private void setupBookingTable() {
        colbookingdetailID.setCellValueFactory(new PropertyValueFactory<>("bookingDetailId"));
        colBookingID.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colAgentID.setCellValueFactory(new PropertyValueFactory<>("agentId"));
        colstartDate.setCellValueFactory(new PropertyValueFactory<>("tripStart"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("tripEnd"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDesctination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colBasePrice.setCellValueFactory(new PropertyValueFactory<>("basePrice"));
        colAgencyCommission.setCellValueFactory(new PropertyValueFactory<>("agencyCommission"));
        colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
        colClass.setCellValueFactory(new PropertyValueFactory<>("className"));
        colFees.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
    }

    public void displayAllPastBookings() {
        // Clear the list for new data
        pastBookingsList.clear();
        try {
            pastBookingsList.setAll(
                    BookingDetailsDAO.getBookingDetailsList()
                            .stream()
                            .filter(bookingDetails -> bookingDetails.agentIdProperty().get() == agentId)
                            .collect(Collectors.toCollection(FXCollections::observableArrayList))
            );
        } catch (SQLException e) { // Catching general Exception instead of SQLException
            AlertBox.showAlert("Error", "Error displaying past bookings.", Alert.AlertType.ERROR);
        }
        // Populate table view
        lvPastBooking.setItems(pastBookingsList);
    }


    @FXML
    private void onResetClick() {
        txtSearch.clear();
    }
}
