package org.example.travelexpertdesktopapplication.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
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
import org.example.travelexpertdesktopapplication.utils.AlertBox;
import org.tinylog.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

    @FXML
    private Button btnReset;


    private ObservableList<Agent> agentList = FXCollections.observableArrayList();
    private FilteredList<Agent> filteredAgents;

    @FXML
    private void initialize() {
        setupTableColumns();
        setupAgencyColumn();
        loadAgents();
        setupSearchFilter();
        setupSelectionListener();
        disableActionButtons();
    }

    @FXML
    private void resetSearch() {
        txtSearch.clear();
        initialize();
    }

    // Setup column bindings for agent attributes
    private void setupTableColumns() {
        colAgentID.setCellValueFactory(new PropertyValueFactory<>("agentID"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("agtFirstName"));
        colMiddleInitial.setCellValueFactory(new PropertyValueFactory<>("agtMiddleInitial"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("agtLastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("agtEmail"));
        colBusPhone.setCellValueFactory(new PropertyValueFactory<>("agtBusPhone"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("agtPosition"));
    }

    // Setup the agency column with caching to avoid repeated DB calls
    private void setupAgencyColumn() {
        Map<Integer, String> agencyCache = new HashMap<>();

        colAgencyID.setCellValueFactory(cellData -> {
            int agencyId = cellData.getValue().getAgencyId();

            // Use cached value if available
            if (!agencyCache.containsKey(agencyId)) {
                try {
                    agencyCache.put(agencyId, AgencyDAO.getAgencyCityById(agencyId));
                } catch (SQLException e) {
                    Logger.error(e, "Error fetching Agency City");
                    AlertBox.showAlert("Error", "Error fetching Agency City", Alert.AlertType.ERROR);
                }
            }
            return new SimpleStringProperty(agencyCache.getOrDefault(agencyId, "Unknown"));
        });
    }

    // Setup search filtering functionality
    private void setupSearchFilter() {
        filteredAgents = new FilteredList<>(agentList, p -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            String lowerCaseFilter = (newValue == null) ? "" : newValue.toLowerCase();

            filteredAgents.setPredicate(agent ->
                    agent.getAgtFirstName().toLowerCase().contains(lowerCaseFilter) ||
                            agent.getAgtLastName().toLowerCase().contains(lowerCaseFilter) ||
                            agent.getAgtEmail().toLowerCase().contains(lowerCaseFilter) ||
                            agent.getAgtBusPhone().toLowerCase().contains(lowerCaseFilter) ||
                            agent.getAgtPosition().toLowerCase().contains(lowerCaseFilter)
            );
        });

        agentTable.setItems(filteredAgents);
    }

    // Setup listener for enabling/disabling edit and delete buttons
    private void setupSelectionListener() {
        agentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean isAgentSelected = (newSelection != null);
            btnEdit.setDisable(!isAgentSelected);
            btnDelete.setDisable(!isAgentSelected);
        });
    }

    // Disable buttons by default
    private void disableActionButtons() {
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
    }


    private void loadAgents() {
        try {
            // Fetch agents from the database
            ObservableList<Agent> agents = FXCollections.observableArrayList(AgentsDAO.getAllAgents());

            if (agents.isEmpty()) {
                Logger.warn("No agents found in the database.");
            }

            agentList.setAll(agents); // Efficiently updates the list
            agentTable.setItems(agentList);
            agentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        } catch (SQLException e) {
            AlertBox.showAlert("Error", "Failed to fetch agents. Please try again.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void handleAddAgent(ActionEvent event) {

        System.out.println("Add Agent button clicked!");

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
                    try{
                    AgentsDAO.deleteAgent(selectedAgent.getAgentID());
                    refreshTable();
                    }catch (SQLException e) {
                        AlertBox.showAlert("Error", "Error in deleting Agent", Alert.AlertType.ERROR);
                    }
                }
            });
        }
    }

    public void openAgentForm(Agent agent) {
        System.out.println("openAgentForm Called");
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
