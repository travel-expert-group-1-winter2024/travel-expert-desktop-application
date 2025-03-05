package org.example.travelexpertdesktopapplication.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.TEDesktopApp;
import org.example.travelexpertdesktopapplication.dao.PackagesDAO;
import org.example.travelexpertdesktopapplication.models.Packages;
import org.example.travelexpertdesktopapplication.models.SupplierContacts;
import org.example.travelexpertdesktopapplication.utils.AlertBox;

public class PackageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private ImageView btnSearch;

//    @FXML
//    private Button btnReset;

    @FXML
    private TableColumn<Packages,Integer> colpkgBasePrice;

    @FXML
    private TableColumn<Packages,Integer> colpkgCommission;

    @FXML
    private TableColumn<Packages,String> colpkgDesc;

    @FXML
    private TableColumn<Packages, LocalDate> colpkgEndDate;

    @FXML
    private TableColumn<Packages,Integer> colpkgID;

    @FXML
    private TableColumn<Packages,String> colpkgName;

    @FXML
    private TableColumn<Packages, LocalDate> colpkgStartDate;

    @FXML
    private TableView<Packages> lvPackages;
    @FXML
    private TextField txtSearch;

    String mode;
    private ObservableList<Packages> packagesList = FXCollections.observableArrayList();
    private FilteredList<Packages> filteredData;


    @FXML
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'package-list-view.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'package-list-view.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'package-list-view.fxml'.";
//        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'package-list-view.fxml'.";
        assert colpkgBasePrice != null : "fx:id=\"colpkgBasePrice\" was not injected: check your FXML file 'package-list-view.fxml'.";
        assert colpkgCommission != null : "fx:id=\"colpkgCommission\" was not injected: check your FXML file 'package-list-view.fxml'.";
        assert colpkgDesc != null : "fx:id=\"colpkgDesc\" was not injected: check your FXML file 'package-list-view.fxml'.";
        assert colpkgEndDate != null : "fx:id=\"colpkgEndDate\" was not injected: check your FXML file 'package-list-view.fxml'.";
        assert colpkgID != null : "fx:id=\"colpkgID\" was not injected: check your FXML file 'package-list-view.fxml'.";
        assert colpkgName != null : "fx:id=\"colpkgName\" was not injected: check your FXML file 'package-list-view.fxml'.";
        assert colpkgStartDate != null : "fx:id=\"colpkgStartDate\" was not injected: check your FXML file 'package-list-view.fxml'.";
        assert lvPackages != null : "fx:id=\"lvPackages\" was not injected: check your FXML file 'package-list-view.fxml'.";

        btnDelete.setDisable(true);
        btnEdit.setDisable(true);

        setupTableForPackages();
        displayPackages();
        filteredData = new FilteredList<>(packagesList, p -> true);

//        btnReset.setOnAction(e->{
//        txtSearch.clear();
//        displayPackages();
//        });

        btnAdd.setOnAction(e->{
            mode="Add";
            openAddEditWindow(null,mode);
        });

        lvPackages.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Packages>() {
            @Override
            public void changed(ObservableValue<? extends Packages> observableValue, Packages oldValue, Packages newValue) {
                int index = lvPackages.getSelectionModel().getSelectedIndex();
                if(lvPackages.getSelectionModel().isSelected(index)) {
                    btnDelete.setDisable(false);
                    btnEdit.setDisable(false);
                    btnEdit.setOnAction(e->{
                        mode = "Edit";
                        openAddEditWindow(newValue, mode);
                    });
                }
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(packages -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true; // Show all if search is empty
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // Use the searchable string for comparison
                return packages.toSearchableStringPackages().contains(lowerCaseFilter);
            });
        });

        SortedList<Packages> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(lvPackages.comparatorProperty());
        lvPackages.setItems(sortedData);
    }

    private void setupTableForPackages() {
        colpkgID.setCellValueFactory(new PropertyValueFactory<Packages, Integer>("packageid"));
        colpkgName.setCellValueFactory(new PropertyValueFactory<Packages, String>("pkgname"));
        colpkgStartDate.setCellValueFactory(cellData -> cellData.getValue().getPkgstartdate());
        colpkgEndDate.setCellValueFactory(cellData -> cellData.getValue().getPkgenddate());
        colpkgDesc.setCellValueFactory(new PropertyValueFactory<Packages, String>("pkgdesc"));
        colpkgBasePrice.setCellValueFactory(new PropertyValueFactory<Packages, Integer>("pkgbaseprice"));
        colpkgCommission.setCellValueFactory(new PropertyValueFactory<Packages, Integer>("pkgagencycommission"));
    }

    private void displayPackages() {
        packagesList.clear();
        packagesList.addAll(PackagesDAO.getPackagesList());
        lvPackages.setItems(packagesList);
        lvPackages.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    protected void openAddEditWindow(Packages packages, String mode){
        FXMLLoader fxmlLoader = new FXMLLoader(TEDesktopApp.class.getResource("/views/add-edit-package-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //get the controller
        AddEditPackageController controller = fxmlLoader.getController();
        controller.setMode(mode);

        //As the mode is edit set the Data
        if(mode.equalsIgnoreCase("Edit")) {
            controller.setPackageData(packages);
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add/Edit SupplierContacts");
        stage.setScene(scene);
        stage.showAndWait();
        displayPackages();
    }

    /**
     * Delete Package as per selected data in Observable
     */
    @FXML
    protected void deletePackage() {
        int selectedPackageID = lvPackages.getSelectionModel().getSelectedItems().get(0).getPackageid();
        int numRows = 0;
        numRows = PackagesDAO.deletePackage(selectedPackageID);
        if (numRows == 1) {
            AlertBox.showAlert("Delete", "The Package has been deleted successfully.", Alert.AlertType.CONFIRMATION);
        } else {
            AlertBox.showAlert("Delete", "Delete operation failed. PackageID may not exist.",Alert.AlertType.ERROR);
        }
        displayPackages();
    }
}
