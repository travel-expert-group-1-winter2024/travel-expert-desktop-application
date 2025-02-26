package org.example.travelexpertdesktopapplication.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.travelexpertdesktopapplication.auth.SessionManager;
import org.example.travelexpertdesktopapplication.dao.CustomerDAO;
import org.example.travelexpertdesktopapplication.dao.SupplierDAO;
import org.example.travelexpertdesktopapplication.models.Customer;
import org.example.travelexpertdesktopapplication.models.SupplierContacts;

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

    private ObservableList<Customer> customerData = FXCollections.observableArrayList();
    private FilteredList<Customer> filteredData;

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

        setupCustomerTable();
        displayAllCustomerData();
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
        } catch (Exception e) { // Catching general Exception instead of SQLException
            System.err.println("Failed to load fees table: " + e.getMessage());
            e.printStackTrace();
        }
        // Populate table view
        lvCustomers.setItems(customerData);
    }

    public void displayAllAgentsCustomerData(int AgentID) {
        // Clear the list for new data
        customerData.clear();
        try {
            customerData.setAll(CustomerDAO.getAgentCustomers(AgentID));
        } catch (Exception e) { // Catching general Exception instead of SQLException
            System.err.println("Failed to load fees table: " + e.getMessage());
            e.printStackTrace();
        }
        // Populate table view
        lvCustomers.setItems(customerData);
    }


}
