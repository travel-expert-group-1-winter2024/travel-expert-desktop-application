package org.example.travelexpertdesktopapplication.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.TEDesktopApp;
import org.example.travelexpertdesktopapplication.auth.SessionManager;
import org.example.travelexpertdesktopapplication.dao.CustomerDAO;
import org.example.travelexpertdesktopapplication.dao.SupplierDAO;
import org.example.travelexpertdesktopapplication.models.Customer;
import org.example.travelexpertdesktopapplication.models.Customer;
import org.example.travelexpertdesktopapplication.models.SupplierContacts;
import org.example.travelexpertdesktopapplication.utils.AlertBox;

public class CustomerListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Customer,String> colAddress;

    @FXML
    private TableColumn<Customer,Integer> colAgencyID;

    @FXML
    private TableColumn<Customer,String> colBusinessPhone;

    @FXML
    private TableColumn<Customer,String> colCity;

    @FXML
    private TableColumn<Customer,String> colEmail;

    @FXML
    private TableColumn<Customer,String> colFirstName;

    @FXML
    private TableColumn<Customer,String> colHomePhone;

    @FXML
    private TableColumn<Customer,String> colLastName;

    @FXML
    private TableColumn<Customer,String> colPostalCode;

    @FXML
    private TableColumn<Customer,String> colProvince;

    @FXML
    private TableColumn<Customer,String> colCountry;

    @FXML
    private TableView<Customer> lvCustomers;
    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnEditCustomer;
    @FXML
    private TextField txtSearch;

    private ObservableList<Customer> customerData = FXCollections.observableArrayList();
    private FilteredList<Customer> filteredData;
    String mode;

    private final SessionManager sessionManager = SessionManager.getInstance();
    private int agentId;

    @FXML
    void initialize() {
        assert colAddress != null : "fx:id=\"colAddress\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert colAgencyID != null : "fx:id=\"colAgencyID\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert colBusinessPhone != null : "fx:id=\"colBusinessPhone\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert colCity != null : "fx:id=\"colCity\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert colEmail != null : "fx:id=\"colEmail\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert colFirstName != null : "fx:id=\"colFirstName\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert colHomePhone != null : "fx:id=\"colHomePhone\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert colLastName != null : "fx:id=\"colLastName\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert colPostalCode != null : "fx:id=\"colPostalCode\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert colProvince != null : "fx:id=\"colProvince\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert colCountry != null : "fx:id=\"colCountry\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert lvCustomers != null : "fx:id=\"lvCustomers\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert txtSearch != null : "fx:id=\"txtSearch\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert btnEditCustomer != null : "fx:id=\"btnEditCustomer\" was not injected: check your FXML file 'customer-view.fxml'.";

        agentId = sessionManager.getUser().getAgentId();

        setupCustomerTable();
        displayAllAgentsCustomerData(agentId);
        btnEditCustomer.setDisable(true);

        filteredData = new FilteredList<>(customerData, p -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true; // Show all if search is empty
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // Use the searchable string for comparison
                return customer.toSearchableString().contains(lowerCaseFilter);
            });
        });
        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(lvCustomers.comparatorProperty());
        lvCustomers.setItems(sortedData);

        lvCustomers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observableValue, Customer oldValue, Customer newValue) {
                int index = lvCustomers.getSelectionModel().getSelectedIndex();
                if(lvCustomers.getSelectionModel().isSelected(index)) {
                    btnEditCustomer.setDisable(false);
                    btnEditCustomer.setOnAction(e->{
                        openEditWindow(newValue);
                    });
                }
            }
        });

//        System.out.println(SessionManager.getUser().getRole());
//        System.out.println(SessionManager.getUser().getId());

//        if(SessionManager.getUser().getRole().equals("ADMIN")){
//            displayAllCustomerData();
//        } else {
//            displayAllAgentsCustomerData(4);
//        }


    }

    /**
     * Setup data for each column and bind them from database columns
     */
    private void setupCustomerTable(){
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("custfirstname"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("custlastname"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("custaddress"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("custcity"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("custprov"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("custpostal"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("custcountry"));
        colHomePhone.setCellValueFactory(new PropertyValueFactory<>("custhomephone"));
        colBusinessPhone.setCellValueFactory(new PropertyValueFactory<>("custbusphone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("custemail"));
        colAgencyID.setCellValueFactory(new PropertyValueFactory<>("agentid"));

    }

    /**
     * Display all Data for Suppliers
     */
    public void displayAllCustomerData() {
        // Clear the list for new data
        customerData.clear();
        try {
            customerData.setAll(CustomerDAO.getCustomerList());
        } catch (SQLException e) {
            AlertBox.showAlert("Error","Error displaying customers", Alert.AlertType.ERROR);
        }
        // Populate table view
        lvCustomers.setItems(customerData);
    }

    public void displayAllAgentsCustomerData(int AgentID) {
        // Clear the list for new data
        customerData.clear();
        try {
            customerData.setAll(CustomerDAO.getAgentCustomers(AgentID));
        } catch (Exception e) {
            System.err.println("Failed to load fees table: " + e.getMessage());
            e.printStackTrace();
        }
        // Populate table view
        lvCustomers.setItems(customerData);
    }

    @FXML
    private void onResetClick(){
        txtSearch.clear();
        initialize();
    }

    protected void openEditWindow(Customer Customer){
        FXMLLoader fxmlLoader = new FXMLLoader(TEDesktopApp.class.getResource("/views/edit-customer-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //get the controller
        EditCustomerController controller = fxmlLoader.getController();
        controller.setCustomerData(Customer);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit Customer");
        stage.setScene(scene);
        stage.showAndWait();
        displayAllAgentsCustomerData(agentId);
    }


}
