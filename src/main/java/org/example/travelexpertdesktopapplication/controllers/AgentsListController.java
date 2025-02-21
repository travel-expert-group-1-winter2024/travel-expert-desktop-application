package org.example.travelexpertdesktopapplication.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.dao.AgencyDAO;
import org.example.travelexpertdesktopapplication.dao.AgentsDAO;
import org.example.travelexpertdesktopapplication.models.Agent;

import java.io.IOException;

public class AgentsListController {
    @FXML
    private TableView<Agent> agentTable;

    @FXML
    private TableColumn<Agent, Integer> colAgentID;

    @FXML
    private TableColumn<Agent, String> colFirstName, colLastName,colMiddleInitial, colEmail, colBusPhone, colPosition, colAgencyID;

    @FXML
    private ImageView btnSearch;

    @FXML
    private TextField txtSearch;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEdit;


    private ObservableList<Agent> agentList = FXCollections.observableArrayList();
    private FilteredList<Agent> filteredAgents;

    @FXML
    private void initialize() {
        colAgentID.setCellValueFactory(new PropertyValueFactory<>("agentID"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("agtFirstName"));
        colMiddleInitial.setCellValueFactory(new PropertyValueFactory<>("agtMiddleInitial"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("agtLastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("agtEmail"));
        colBusPhone.setCellValueFactory(new PropertyValueFactory<>("agtBusPhone"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("agtPosition"));

        // Custom cell value factory for the agency column
        colAgencyID.setCellValueFactory(cellData -> {
            int agencyId = cellData.getValue().getAgencyId();
            String agencyCity = AgencyDAO.getAgencyCityById(agencyId);
            return new javafx.beans.property.SimpleStringProperty(agencyCity);
        });

        // Load data into the table
        loadAgents();

        // Set up filtered list
        filteredAgents = new FilteredList<>(agentList, p -> true);

        // Bind the search text to the filtered list
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAgents.setPredicate(agent -> {
                // If search text is empty, display all agents
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convert search text to lowercase for case-insensitive search
                String lowerCaseFilter = newValue.toLowerCase();

                // Check if any field matches the search text
                if (agent.getAgtFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (agent.getAgtLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (agent.getAgtEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (agent.getAgtBusPhone().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (agent.getAgtPosition().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (agent.getAgtPosition().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // No match
            });
        });

        // Bind the filtered list to the table
        agentTable.setItems(filteredAgents);

        // Disable buttons by default
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);

        // Enable buttons when an agent is selected
        agentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean isAgentSelected = newSelection != null;
            btnEdit.setDisable(!isAgentSelected);
            btnDelete.setDisable(!isAgentSelected);
        });
    }

    private void loadAgents() {
        // Fetch agents from the database
        agentList.clear();
        agentList.addAll(AgentsDAO.getAllAgents());
        agentTable.setItems(agentList);
        agentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void handleAddAgent(ActionEvent event) {
        openAgentForm(null);
    }

    @FXML
    private void handleEditAgent() {
        Agent selectedAgent = agentTable.getSelectionModel().getSelectedItem();
        if (selectedAgent != null) {
            openAgentForm(selectedAgent);
        }
    }

    @FXML
    private void handleDeleteAgent() {
        Agent selectedAgent = agentTable.getSelectionModel().getSelectedItem();
        if (selectedAgent != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Agent");
            alert.setHeaderText("Are you sure you want to delete this agent?");
            alert.setContentText("This action cannot be undone.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    AgentsDAO.deleteAgent(selectedAgent.getAgentID());
                    refreshTable();
                }
            });
        }
    }

    public void openAgentForm(Agent agent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/agents-form.fxml"));
            Parent root = loader.load();

            AgentsFormController controller = loader.getController();
            if (agent != null) {
                controller.setAgentData(agent);
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(agent == null ? "Add New Agent" : "Edit Existing Agent");
            stage.showAndWait();
            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshTable() {
        try{
        agentTable.setItems(AgentsDAO.getAllAgents());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
