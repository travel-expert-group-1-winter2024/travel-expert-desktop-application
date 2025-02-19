package org.example.travelexpertdesktopapplication.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.TEDesktopApp;
import org.example.travelexpertdesktopapplication.dao.SupplierDAO;
import org.example.travelexpertdesktopapplication.models.SupplierContacts;
import org.example.travelexpertdesktopapplication.utils.AlertBox;

public class SupplierController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddSupplier;
    @FXML
    private Button btnEditSupplier;

    @FXML
    private Button btnDeleteSupplier;

    @FXML
    private TableColumn<SupplierContacts, String> colAddress;

    @FXML
    private TableColumn<SupplierContacts, String> colBusinessPhone;

    @FXML
    private TableColumn<SupplierContacts, String> colCity;

    @FXML
    private TableColumn<SupplierContacts, String> colCompany;

    @FXML
    private TableColumn<SupplierContacts, Integer> colContactID;

    @FXML
    private TableColumn<SupplierContacts, String> colCountry;

    @FXML
    private TableColumn<SupplierContacts, String> colEmail;

    @FXML
    private TableColumn<SupplierContacts, String> colFax;

    @FXML
    private TableColumn<SupplierContacts, String> colFirstName;

    @FXML
    private TableColumn<SupplierContacts, String> colLastName;

    @FXML
    private TableColumn<SupplierContacts, String> colPostalCode;

    @FXML
    private TableColumn<SupplierContacts, String> colProvince;

    @FXML
    private TableColumn<SupplierContacts, Integer> colSupplierID;

    @FXML
    private TableColumn<SupplierContacts,String> colAffliationid;

    @FXML
    private TableColumn<SupplierContacts,String> colWebsite;

    @FXML
    private TableView<SupplierContacts> tvSuppliers;

    private ObservableList<SupplierContacts> data = FXCollections.observableArrayList();
    public String mode;

    @FXML
    void initialize() {
        assert btnAddSupplier != null : "fx:id=\"btnAddSupplier\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert btnDeleteSupplier != null : "fx:id=\"btnDeleteSupplier\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert colAddress != null : "fx:id=\"colAddress\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert colAffliationid != null : "fx:id=\"colAffliationid\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert colBusinessPhone != null : "fx:id=\"colBusinessPhone\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert colCity != null : "fx:id=\"colCity\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert colCompany != null : "fx:id=\"colCompany\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert colContactID != null : "fx:id=\"colContactID\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert colCountry != null : "fx:id=\"colCountry\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert colEmail != null : "fx:id=\"colEmail\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert colFax != null : "fx:id=\"colFax\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert colFirstName != null : "fx:id=\"colFirstName\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert colLastName != null : "fx:id=\"colLastName\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert colPostalCode != null : "fx:id=\"colPostalCode\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert colProvince != null : "fx:id=\"colProvince\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert colSupplierID != null : "fx:id=\"colSupplierID\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert colWebsite != null : "fx:id=\"colWebsite\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert tvSuppliers != null : "fx:id=\"tvSuppliers\" was not injected: check your FXML file 'supplier-list-view.fxml'.";
        assert btnEditSupplier != null : "fx:id=\"btnAddSupplier\" was not injected: check your FXML file 'supplier-list-view.fxml'.";

        //Initialize and setup table and display data
        setupSupplierTable();
        displayAllSupplierData();
        btnDeleteSupplier.setDisable(true); //disable delete Button
        btnEditSupplier.setDisable(true);   //disable Edit  Button

        //on Add set action
        btnAddSupplier.setOnAction(e -> {
            mode = "Add";
            openAddEditWindow(null, mode);
        });

        //on selection of data add listener
        tvSuppliers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SupplierContacts>() {
            @Override
            public void changed(ObservableValue<? extends SupplierContacts> observableValue, SupplierContacts oldValue, SupplierContacts newValue) {
                int index = tvSuppliers.getSelectionModel().getSelectedIndex();
                if(tvSuppliers.getSelectionModel().isSelected(index)) {
                    btnDeleteSupplier.setDisable(false);
                    btnEditSupplier.setDisable(false);
                    btnEditSupplier.setOnAction(e->{
                        mode = "Edit";
                        openAddEditWindow(newValue, mode);
                    });
                }
            }
        });
    }

    /**
     * Setup data for each column and bind them from database columns
     */
    private void setupSupplierTable(){
        colContactID.setCellValueFactory(new PropertyValueFactory<>("suppliercontactid"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("supconfirstname"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("supconlastname"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("supconcompany"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("supconcity"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("supconprov"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("supconpostal"));
        colBusinessPhone.setCellValueFactory(new PropertyValueFactory<>("supconbusphone"));
        colFax.setCellValueFactory(new PropertyValueFactory<>("supconfax"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("supconcountry"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("supconemail"));
        colWebsite.setCellValueFactory(new PropertyValueFactory<>("supconurl"));
        colAffliationid.setCellValueFactory(new PropertyValueFactory<>("affiliationid"));
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierid"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("supconaddress"));
    }

    /**
     * Display all Data for Suppliers
     */
    public void displayAllSupplierData() {
        // Clear the list for new data
        data.clear();
        try {
            data.setAll(SupplierDAO.getSupplierList());
        } catch (Exception e) { // Catching general Exception instead of SQLException
            System.err.println("Failed to load fees table: " + e.getMessage());
            e.printStackTrace();
        }
        // Populate table view
        tvSuppliers.setItems(data);
    }

    /**
     * Opens a new window of Add/Edit
     * @param supplierContacts-Object with all the data
     * @param mode - Sets teh data as per Mode
     */
    private void openAddEditWindow(SupplierContacts supplierContacts, String mode){
        FXMLLoader fxmlLoader = new FXMLLoader(TEDesktopApp.class.getResource("/views/add-edit-supplier-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //get the controller
        AddEditSupplierController controller = fxmlLoader.getController();
        controller.setMode(mode);

        //As the mode is edit set the Data
        if(mode.equalsIgnoreCase("Edit")) {
            controller.setSupplierData(supplierContacts);
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add/Edit SupplierContacts");
        stage.setScene(scene);
        stage.showAndWait();
        displayAllSupplierData();
    }


    /**
     * Delete Supplier as per selected data in Observable
     */
    @FXML
    private void deleteSupplier(){
        int selectedSupplierContactID = tvSuppliers.getSelectionModel().getSelectedItems().get(0).getSuppliercontactid();
        int numRows = 0;
        numRows = SupplierDAO.deleteSelectedSupplierContact(selectedSupplierContactID);
        if (numRows == 1) {
            AlertBox.showAlert("Delete", "The Supplier Contact has been deleted successfully.",Alert.AlertType.CONFIRMATION);
        } else {
            AlertBox.showAlert("Delete", "Delete operation failed. Supplier Contacts ID may not exist.",Alert.AlertType.ERROR);
        }
        displayAllSupplierData();
    }
}
