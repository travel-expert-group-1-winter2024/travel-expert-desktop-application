package org.example.travelexpertdesktopapplication.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Button btnAdd, btnEdit, btnDelete ;

    private ObservableList<Agency> agencyList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
//        agencyList.setAll();
        colAgencyID.setCellValueFactory(new PropertyValueFactory<Agency, Integer>("agencyID"));
        colAgencyAddress.setCellValueFactory(new PropertyValueFactory<Agency, String>("agncyAddress"));
        colAgencyCity.setCellValueFactory(new PropertyValueFactory<Agency, String>("agncyCity"));
        colAgencyCountry.setCellValueFactory(new PropertyValueFactory<Agency, String>("agncyCountry"));
        colAgencyFax.setCellValueFactory(new PropertyValueFactory<Agency, String>("agncyFax"));
        colAgencyPhone.setCellValueFactory(new PropertyValueFactory<Agency, String>("agncyPhone"));
        colAgencyProv.setCellValueFactory(new PropertyValueFactory<Agency, String>("agncyProv"));
        colAgencyPostal.setCellValueFactory(new PropertyValueFactory<Agency, String>("agncyPostal"));


        System.out.println("AgencyListController initialize");
        agencyTable.setItems(AgencyDAO.getAllAgencies());
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
        agencyTable.getItems().clear();
        agencyTable.getItems().addAll(AgencyDAO.getAllAgencies()); // Fetch updated list from DB
    }

}
