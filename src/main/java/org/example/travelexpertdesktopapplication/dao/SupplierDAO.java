package org.example.travelexpertdesktopapplication.dao;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.travelexpertdesktopapplication.models.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.travelexpertdesktopapplication.services.DatabaseConnection.getConnection;

public class SupplierDAO {

    //get the supplier data form the database
    public static ObservableList<Supplier> getSupplierList() {
        ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
        String query = "SELECT * FROM suppliercontacts;";
        //check the connection
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            //Append data in the list
            while (rs.next()) {
                Supplier supplier = new Supplier(
                        new SimpleIntegerProperty(rs.getInt("suppliercontactid")),
                        new SimpleStringProperty(rs.getString("supconfirstname")),
                        new SimpleStringProperty(rs.getString("supconlastname")),
                        new SimpleStringProperty(rs.getString("supconcompany")),
                        new SimpleStringProperty(rs.getString("supconcity")),
                        new SimpleStringProperty(rs.getString("supconprov")),
                        new SimpleStringProperty(rs.getString("supconpostal")),
                        new SimpleStringProperty(rs.getString("supconbusphone")),
                        new SimpleStringProperty(rs.getString("supconfax")),
                        new SimpleStringProperty(rs.getString("supconcountry")),
                        new SimpleStringProperty(rs.getString("supconemail")),
                        new SimpleStringProperty(rs.getString("supconurl")),
                        new SimpleStringProperty(rs.getString("affiliationid")),
                        new SimpleIntegerProperty(rs.getInt("supplierid")),
                        new SimpleStringProperty(rs.getString("supconaddress"))
                );
                supplierList.add(supplier);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving Fees data!");
            e.printStackTrace();
        }
        return supplierList;
    }
}
