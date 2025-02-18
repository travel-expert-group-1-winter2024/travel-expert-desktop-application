package org.example.travelexpertdesktopapplication.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.TEDesktopApp;
import org.example.travelexpertdesktopapplication.dao.SupplierDAO;
import org.example.travelexpertdesktopapplication.models.Supplier;

public class SupplierController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddSupplier;

    @FXML
    private Button btnDeleteSupplier;

    @FXML
    private TableColumn<Supplier, String> colAddress;

    @FXML
    private TableColumn<Supplier, String> colBusinessPhone;

    @FXML
    private TableColumn<Supplier, String> colCity;

    @FXML
    private TableColumn<Supplier, String> colCompany;

    @FXML
    private TableColumn<Supplier, Integer> colContactID;

    @FXML
    private TableColumn<Supplier, String> colCountry;

    @FXML
    private TableColumn<Supplier, String> colEmail;

    @FXML
    private TableColumn<Supplier, String> colFax;

    @FXML
    private TableColumn<Supplier, String> colFirstName;

    @FXML
    private TableColumn<Supplier, String> colLastName;

    @FXML
    private TableColumn<Supplier, String> colPostalCode;

    @FXML
    private TableColumn<Supplier, String> colProvince;

    @FXML
    private TableColumn<Supplier, Integer> colSupplierID;

    @FXML
    private TableColumn<Supplier,String> colAffliationid;

    @FXML
    private TableColumn<Supplier,String> colWebsite;

    @FXML
    private TableView<Supplier> tvSuppliers;

    private ObservableList<Supplier> data = FXCollections.observableArrayList();
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

        setupSupplierTable();
        displayAllSupplierData();
        btnDeleteSupplier.setDisable(true);

        //on Add set action
        btnAddSupplier.setOnAction(e -> {
            mode = "Add";
            openAddEditWindow(null, mode);
        });
        //on selection of data add listener
        tvSuppliers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Supplier>() {
            @Override
            public void changed(ObservableValue<? extends Supplier> observableValue, Supplier oldValue, Supplier newValue) {
                int index = tvSuppliers.getSelectionModel().getSelectedIndex();
                if(tvSuppliers.getSelectionModel().isSelected(index)) {
                    Platform.runLater(() -> {
                        mode = "Edit";
                        openAddEditWindow(newValue, mode);
                    });
                }
            }
        });
    }

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

    //Display all Data for fees
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

    private void openAddEditWindow(Supplier supplier, String mode){
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
//            controller.setFeeDataForm(Fees);
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add/Edit Supplier");
        stage.setScene(scene);
        stage.showAndWait();
        displayAllSupplierData();
    }
}
