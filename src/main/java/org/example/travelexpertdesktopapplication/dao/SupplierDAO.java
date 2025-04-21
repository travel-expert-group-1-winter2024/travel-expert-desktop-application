package org.example.travelexpertdesktopapplication.dao;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.travelexpertdesktopapplication.models.Supplier;
import org.example.travelexpertdesktopapplication.models.SupplierContacts;
import org.tinylog.Logger;

import java.sql.*;

public class SupplierDAO {

    /**
     * Get the supplier data from the database
     * @return - whole supplier contact list
     */
    public static ObservableList<SupplierContacts> getSupplierList() throws SQLException {
        ObservableList<SupplierContacts> supplierContactsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM suppliercontacts ORDER BY suppliercontactid";
        Logger.debug("Fetching supplier contacts from the database.");

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            Logger.debug("Executing query: {}", query);
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
                        new SimpleStringProperty(rs.getString("supconcountry")),
                        new SimpleStringProperty(rs.getString("supconbusphone")),
                        new SimpleStringProperty(rs.getString("supconfax")),
                        new SimpleStringProperty(rs.getString("supconemail")),
                        new SimpleStringProperty(rs.getString("supconurl")),
                        new SimpleStringProperty(rs.getString("affiliationid")),
                        new SimpleIntegerProperty(rs.getInt("supplierid"))
                );
                supplierContactsList.add(supplierContacts);
            }
            Logger.info("Retrieved {} supplier contacts.", supplierContactsList.size());
        } catch (SQLException e) {
            Logger.error(e, "Error retrieving supplier contacts.");
            throw e;
        }
        return supplierContactsList;
    }

    /**
     * Get affiliations from the database
     * @return - affiliations list
     */
    public static ObservableList<String> getAffiliations() throws SQLException{
        ObservableList<String> affiliations = FXCollections.observableArrayList();
        String query = "SELECT affilitationid FROM affiliations ";
        Logger.debug("Fetching affiliations from the database.");

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            Logger.debug("Executing query: {}", query);
            while (rs.next()) {
                affiliations.add(rs.getString("affilitationid"));
            }
            Logger.info("Retrieved {} affiliations.", affiliations.size());
        } catch (SQLException e) {
            Logger.debug(e, "Error retrieving affiliations.");
            throw e;
        }
        return affiliations;
    }

    /**
     * Delete supplier contact based on ID
     * @param contactSupplierID - passed for deleting the data
     * @return affected rows
     */
    public static int deleteSelectedSupplierContact(int contactSupplierID) throws SQLException {
        String sql = "DELETE FROM suppliercontacts WHERE suppliercontactid = ?";
        Logger.debug("Deleting supplier contact with ID: {}", contactSupplierID);
        int affectedRows = 0;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, contactSupplierID);
            Logger.debug("Executing query: {}", sql);
            affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                Logger.info("Deleted supplier contact successfully. ID={}", contactSupplierID);
            } else {
                Logger.warn("No supplier contact deleted. Possibly invalid ID: {}", contactSupplierID);
            }
        } catch (SQLException e) {
            Logger.error(e, "Error deleting supplier contact with ID {}", contactSupplierID);
            throw e;
        }
        return affectedRows;
    }

    /**
     * Update Data to the database - Update Operation
     * @param supplierContacts - Object of data
     * @return-  affected rows
     */
    public static int updateSupplierContact(SupplierContacts supplierContacts) throws SQLException {
        String sql = "UPDATE suppliercontacts SET supconfirstname = ?, supconlastname = ?, supconcompany = ?, " +
                "supconaddress = ?, supconcity = ?, supconprov = ?, supconpostal = ?, supconcountry = ?, " +
                "supconbusphone = ?, supconfax = ?, supconemail = ?, supconurl = ?, affiliationid = ?, supplierid = ? " +
                "WHERE suppliercontactid = ?";

        Logger.debug("Updating supplier contact. ID={}", supplierContacts.getSuppliercontactid());
        Logger.debug("Executing query: {}", sql);

        int numAffectedRows = 0;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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

            if (numAffectedRows > 0) {
                Logger.info("Supplier contact updated successfully. ID={}", supplierContacts.getSuppliercontactid());
            } else {
                Logger.warn("No supplier contact updated. Possibly invalid ID: {}", supplierContacts.getSuppliercontactid());
            }

        } catch (SQLException e) {
            Logger.error(e, "Error updating supplier contact. ID={}", supplierContacts.getSuppliercontactid());
            throw e;
        }

        return numAffectedRows;
    }

    /**
     * Add new data to the Supplier Contact Table - Insert Operation
     * @param supplierContacts - Object of data
     * @return affected rows
     */
    public static int addSupplierContact(SupplierContacts supplierContacts) throws SQLException{
        int numAffectedRows = 0;
        int addSupplierID = addSupplier(supplierContacts);

        Logger.debug("Adding new supplier contact: {}", supplierContacts);
        if (addSupplierID == 0) {
            Logger.warn("Failed to add supplier. Supplier ID not generated.");
            return 0;
        }

        String sql = "INSERT INTO suppliercontacts (supconfirstname, supconlastname, supconcompany, supconaddress, " +
                "supconcity, supconprov, supconpostal, supconcountry, supconbusphone, supconfax, supconemail, " +
                "supconurl, affiliationid, supplierid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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

            Logger.debug("Executing query: {}", sql);
            numAffectedRows = stmt.executeUpdate();
            Logger.info("Supplier contact added successfully.");
        } catch (SQLException e) {
            Logger.error(e, "Error adding supplier contact.");
            throw e;
        }
        return numAffectedRows;
    }

    /**
     * Add supplier to the Supplier table
     * @param supplier - data of supplier
     * @return generated supplier ID
     */
    public static int addSupplier(SupplierContacts supplier) throws SQLException {
        int numAffectedRows = 0;
        String sql = "INSERT INTO suppliers (supname) VALUES (?) RETURNING supplierid;";
        Logger.debug("Adding new supplier: {}", supplier.getSupconcompany());

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet generatedKeys = stmt.executeQuery()) {

            stmt.setString(1, supplier.getSupconcompany());
            Logger.debug("Executing query: {}", sql);

            if (generatedKeys.next()) {
                numAffectedRows = generatedKeys.getInt(1);
                supplier.setSupplierid(numAffectedRows);
                Logger.info("Generated Supplier ID: {}", numAffectedRows);
            }
        } catch (SQLException e) {
            Logger.error(e, "Error adding supplier.");
            throw e;
        }
        return numAffectedRows;
    }

    public static ObservableList<Supplier> getAllSuppliers() throws SQLException {
        ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
        String query = "SELECT * FROM suppliers";
        Logger.debug("Fetching all suppliers from the database.");

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            Logger.debug("Executing query: {}", query);
            while (rs.next()) {
                Supplier supplier = new Supplier(
                        new SimpleIntegerProperty(rs.getInt("supplierid")),
                        new SimpleStringProperty(rs.getString("supname"))
                );
                suppliers.add(supplier);
            }
            Logger.info("Retrieved {} suppliers.", suppliers.size());
        } catch (SQLException e) {
            Logger.error(e, "Error retrieving suppliers.");
            throw e;
        }
        return suppliers;
    }

    public static Supplier getSupplierById(int supplierId) throws SQLException {
        String sql = "SELECT supplierid, supname FROM suppliers WHERE supplierid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, supplierId);
            ResultSet rs = stmt.executeQuery();

            Logger.debug("Executing query: {}", sql);

            if (rs.next()) {
                return new Supplier(
                        new SimpleIntegerProperty(rs.getInt("supplierid")),
                        new SimpleStringProperty(rs.getString("supname"))
                );
            }
        } catch (SQLException e) {
            Logger.error(e, "Error fetching supplier with ID: {}", supplierId);
            throw e;
        }
        return null; // Return null if no supplier is found
    }
}