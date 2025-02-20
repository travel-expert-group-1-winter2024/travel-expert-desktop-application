package org.example.travelexpertdesktopapplication.dao;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.travelexpertdesktopapplication.models.SupplierContacts;

import java.sql.*;

import static org.example.travelexpertdesktopapplication.services.DatabaseConnection.getConnection;

public class SupplierDAO {

    /**
     * get the supplier data form the database
     * @return - whole supplier contact list
     */
    public static ObservableList<SupplierContacts> getSupplierList() {
        ObservableList<SupplierContacts> supplierContactsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM suppliercontacts;";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            //Append data in the list
            while (rs.next()) {
                SupplierContacts supplierContacts = new SupplierContacts(
                        new SimpleIntegerProperty(rs.getInt("suppliercontactid")),
                        new SimpleStringProperty(rs.getString("supconfirstname")),
                        new SimpleStringProperty(rs.getString("supconlastname")),
                        new SimpleStringProperty(rs.getString("supconcompany")),
                        new SimpleStringProperty(rs.getString("supconaddress")),
                        new SimpleStringProperty(rs.getString("supconcity")),
                        new SimpleStringProperty(rs.getString("supconprov")),
                        new SimpleStringProperty(rs.getString("supconpostal")),
                        new SimpleStringProperty(rs.getString("supconcountry")), new SimpleStringProperty(rs.getString("supconbusphone")),
                        new SimpleStringProperty(rs.getString("supconfax")),
                        new SimpleStringProperty(rs.getString("supconemail")),
                        new SimpleStringProperty(rs.getString("supconurl")),
                        new SimpleStringProperty(rs.getString("affiliationid")),
                        new SimpleIntegerProperty(rs.getInt("supplierid"))
                );
                supplierContactsList.add(supplierContacts);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving Fees data!");
            e.printStackTrace();
        }
        return supplierContactsList;
    }

    /**
     * Get affiliations from the database
     * @return - affilications list
     */
    public static ObservableList<String> getAffiliations() {
        ObservableList<String> affiliations = FXCollections.observableArrayList();
        String query = "SELECT affilitationid FROM affiliations";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                affiliations.add(rs.getString("affilitationid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return affiliations;
    }

    /**
     * Delete selected on the basis of ID
     * @param contactSupplierID - passed for deleting the data
     * @return affected rows
     */
    public static int deleteSelectedSupplierContact(int contactSupplierID) {
        Connection conn = getConnection();
        int affectedRows = 0;
        String sql = "DELETE FROM suppliercontacts WHERE suppliercontactid = ?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, contactSupplierID);
            affectedRows = stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return affectedRows;
    }

    /**
     * Add new Data to the Supplier Contact Table - Insert Operation
     * @param supplierContacts - Object of data
     * @return-  affected rows
     */
    public static int addSupplierContact(SupplierContacts supplierContacts) {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        int addSupplierID = addSupplier(supplierContacts);
        try {
            if(addSupplierID != 0){
                String sql = "INSERT INTO suppliercontacts ( supconfirstname, supconlastname, supconcompany, supconaddress, " +
                        "supconcity, supconprov, supconpostal, supconcountry, supconbusphone, supconfax, supconemail, " +
                        "supconurl, affiliationid, supplierid) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, supplierContacts.getSupconfirstname());
                stmt.setString(2, supplierContacts.getSupconlastname());
                stmt.setString(3, supplierContacts.getSupconcompany());
                stmt.setString(4, supplierContacts.getSupconaddress());
                stmt.setString(5, supplierContacts.getSupconcity());
                stmt.setString(6, supplierContacts.getSupconprov());
                stmt.setString(7, supplierContacts.getSupconpostal());
                stmt.setString(8, supplierContacts.getSupconcountry());
                stmt.setString(9, supplierContacts.getSupconbusphone());
                stmt.setString(10, supplierContacts.getSupconfax());
                stmt.setString(11, supplierContacts.getSupconemail());
                stmt.setString(12, supplierContacts.getSupconurl());
                stmt.setString(13, supplierContacts.getAffiliationid());
                stmt.setInt(14, supplierContacts.getSupplierid());
                numAffectedRows = stmt.executeUpdate();
                conn.close();
            }
            return numAffectedRows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Data to the database - Update Operation
     * @param supplierContacts - Object of data
     * @return-  affected rows
     */
    public static int updateSupplierContact(SupplierContacts supplierContacts){
        Connection conn = getConnection();
        int numAffectedRows = 0;
        try {
            String sql = "UPDATE suppliercontacts SET supconfirstname = ?, supconlastname = ?, supconcompany = ?, " +
                    "supconaddress = ?, supconcity = ?, supconprov = ?, supconpostal = ?, supconcountry = ?, " +
                    "supconbusphone = ?, supconfax = ?, supconemail = ?, supconurl = ?, affiliationid = ?, supplierid = ? " +
                    "WHERE suppliercontactid = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, supplierContacts.getSupconfirstname());
            stmt.setString(2, supplierContacts.getSupconlastname());
            stmt.setString(3, supplierContacts.getSupconcompany());
            stmt.setString(4, supplierContacts.getSupconaddress());
            stmt.setString(5, supplierContacts.getSupconcity());
            stmt.setString(6, supplierContacts.getSupconprov());
            stmt.setString(7, supplierContacts.getSupconpostal());
            stmt.setString(8, supplierContacts.getSupconcountry());
            stmt.setString(9, supplierContacts.getSupconbusphone());
            stmt.setString(10, supplierContacts.getSupconfax());
            stmt.setString(11, supplierContacts.getSupconemail());
            stmt.setString(12, supplierContacts.getSupconurl());
            stmt.setString(13, supplierContacts.getAffiliationid());
            stmt.setInt(14, supplierContacts.getSupplierid());
            stmt.setInt(15, supplierContacts.getSuppliercontactid());

            numAffectedRows = stmt.executeUpdate();
            conn.close();
            return numAffectedRows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add supplier to the Supplier table
     * @param supplier data of supplier
     * @return
     */
    public static int addSupplier(SupplierContacts supplier){
        Connection conn = getConnection();
        int generatedId = 0;
        String sql = "Insert INTO suppliers (supname) VALUES (?) RETURNING supplierid;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, supplier.getSupconcompany());

            try (ResultSet generatedKeys = stmt.executeQuery()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                    supplier.setSupplierid(generatedId); // Update the object with the new ID
                    System.out.println("Generated Supplier ID: " + generatedId);
                }
            }
            return generatedId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
