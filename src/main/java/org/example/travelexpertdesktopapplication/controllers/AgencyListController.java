package org.example.travelexpertdesktopapplication.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.auth.Agent;
import org.example.travelexpertdesktopapplication.dao.AgencyDAO;
import org.example.travelexpertdesktopapplication.models.Agency;

import java.io.IOException;

public class AgencyListController {
    @FXML
    private TableView<Agency> agencyTable;

    @FXML
    private TableColumn<Agency, String> colAgencyAddress, colAgencyCity, colAgencyProv
            , colAgencyPostal, colAgencyCountry, colAgencyPhone, colAgencyFax;

    @FXML
    private TableColumn<Agency, Integer> colAgencyID;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private ImageView btnSearch;

    @FXML
    private TextField txtSearch;

    private ObservableList<Agency> agencyList = FXCollections.observableArrayList();
    private FilteredList<Agency> filteredAgencies;



    @FXML
    private void initialize() {
        colAgencyID.setCellValueFactory(new PropertyValueFactory<Agency, Integer>("agencyID"));
        colAgencyAddress.setCellValueFactory(new PropertyValueFactory<Agency, String>("agncyAddress"));
        colAgencyCity.setCellValueFactory(new PropertyValueFactory<Agency, String>("agncyCity"));
        colAgencyCountry.setCellValueFactory(new PropertyValueFactory<Agency, String>("agncyCountry"));
        colAgencyFax.setCellValueFactory(new PropertyValueFactory<Agency, String>("agncyFax"));
        colAgencyPhone.setCellValueFactory(new PropertyValueFactory<Agency, String>("agncyPhone"));
        colAgencyProv.setCellValueFactory(new PropertyValueFactory<Agency, String>("agncyProv"));
        colAgencyPostal.setCellValueFactory(new PropertyValueFactory<Agency, String>("agncyPostal"));

        // Load agencies into the list
        agencyList.setAll(AgencyDAO.getAllAgencies());

        // Set up filtered list
        filteredAgencies = new FilteredList<>(agencyList, p -> true);

        // Bind the search text to the filtered list
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAgencies.setPredicate(agency -> {
                // If search text is empty, display all agencies
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convert search text to lowercase for case-insensitive search
                String lowerCaseFilter = newValue.toLowerCase();

                // Check if any field matches the search text
                if (agency.getAgncyAddress().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (agency.getAgncyCity().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (agency.getAgncyProv().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (agency.getAgncyPostal().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (agency.getAgncyCountry().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (agency.getAgncyPhone().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (agency.getAgncyFax().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // No match
            });
        });

        // Bind the filtered list to the table
        agencyTable.setItems(filteredAgencies);
        agencyTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Disable buttons by default
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);

        // Enable buttons when an agency is selected
        agencyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean isAgencySelected = newSelection != null;
            btnEdit.setDisable(!isAgencySelected);
            btnDelete.setDisable(!isAgencySelected);
        });
    }


    @FXML
    private void handleAddAgency(ActionEvent event) {
        openAgencyForm(null); // Open form for adding a new fee
    }

    @FXML
    private void handleEditAgency() {
        Agency selectedAgency = agencyTable.getSelectionModel().getSelectedItem();
        if (selectedAgency != null) {
            openAgencyForm(selectedAgency);
        }
    }

    @FXML
    private void handleDeleteAgency() {
        Agency selectedAgency = agencyTable.getSelectionModel().getSelectedItem();
        if (selectedAgency != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Agency");
            alert.setHeaderText("Are you sure you want to delete this agency?");
            alert.setContentText("This action cannot be undone.");

            alert.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    AgencyDAO.deleteAgency(selectedAgency.getAgencyID());
                    refreshTable();
                }
            });
        }
    }

    public void openAgencyForm(Agency agency) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/agency-form.fxml"));
            Parent root = loader.load();

            AgencyFormController controller = loader.getController();
            if (agency != null) {
                controller.setAgencyData(agency);
            }
            Stage stage = new Stage();
            // Change scene
            stage.setScene(new Scene(root));
            stage.setTitle(agency == null ? "Add New Agency" : "Edit Existing Agency");
            stage.showAndWait();
            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshTable() {
        try {
            agencyTable.setItems(AgencyDAO.getAllAgencies()); // Fetch updated list from DB
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
