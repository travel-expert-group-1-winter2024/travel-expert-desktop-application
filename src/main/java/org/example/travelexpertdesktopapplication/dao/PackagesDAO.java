package org.example.travelexpertdesktopapplication.dao;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.travelexpertdesktopapplication.models.Packages;
import org.example.travelexpertdesktopapplication.models.SupplierContacts;
import org.tinylog.Logger;

import java.sql.*;

import static org.example.travelexpertdesktopapplication.dao.DatabaseManager.getConnection;

public class PackagesDAO {

    public static ObservableList<Packages> getPackagesList() {
        ObservableList<Packages> packageList = FXCollections.observableArrayList();
        String query = "SELECT * FROM packages";
        Logger.debug("Fetching all packages from the database.");

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            Logger.debug("Executing query: {}", query);
            while (resultSet.next()) {
                Packages packages = new Packages(
                        new SimpleIntegerProperty(resultSet.getInt("packageid")),
                        new SimpleStringProperty(resultSet.getString("pkgname")),
                        new SimpleObjectProperty<>(resultSet.getDate("pkgstartdate") != null ?
                                resultSet.getDate("pkgstartdate").toLocalDate() : null), // FIXED
                        new SimpleObjectProperty<>(resultSet.getDate("pkgenddate") != null ?
                                resultSet.getDate("pkgenddate").toLocalDate() : null),   // FIXED
                        new SimpleStringProperty(resultSet.getString("pkgdesc")),
                        new SimpleIntegerProperty(resultSet.getInt("pkgbaseprice")),
                        new SimpleIntegerProperty(resultSet.getInt("pkgagencycommission"))
                );
                packageList.add(packages);
            }
        } catch (SQLException e) {
            Logger.error(e, "Error fetching Packages from the database.");
        }
        return packageList;
    }

    public static int deletePackage(int packageID) {
        String sql = "DELETE FROM packages WHERE packageid = ?";
        Logger.debug("Deleting supplier contact with ID: {}", packageID);
        int affectedRows = 0;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, packageID);
            Logger.debug("Executing query: {}", sql);
            affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                Logger.info("Deleted package successfully. ID={}", packageID);
            } else {
                Logger.warn("No package deleted. Possibly invalid ID: {}", packageID);
            }
        } catch (SQLException e) {
            Logger.error(e, "Error deleting package with ID {}", packageID);
        }
        return affectedRows;
    }

    public static int addPackage(Packages p){
        int numAffectedRows =0;
//        Logger.debug("Adding new package: {}", package);

        String sql = "INSERT INTO packages (pkgName, pkgstartdate, pkgenddate, pkgdesc, " +
                "pkgbaseprice, pkgagencycommission) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,p.getPkgname());
            stmt.setDate(2, java.sql.Date.valueOf(p.getPkgstartdate().get()));
            stmt.setDate(3, java.sql.Date.valueOf(p.getPkgenddate().get()));
            stmt.setString(4, p.getPkgdesc());
            stmt.setInt(5, p.getPkgbaseprice());
            stmt.setInt(6, p.getPkgagencycommission());

            Logger.debug("Executing query: {}", sql);
            numAffectedRows = stmt.executeUpdate();
            Logger.info("Supplier contact added successfully.");
        } catch (SQLException e) {
            Logger.error(e, "Error adding supplier contact.");
        }
        return numAffectedRows;
    }

    public static int updatePackeDetails(Packages p) {
        String sql = "UPDATE packages SET pkgName = ?, pkgstartdate = ?, pkgenddate = ?, " +
                "pkgdesc = ?, pkgbaseprice = ?, pkgagencycommission = ? " +
                "WHERE packageid = ?";

        Logger.debug("Updating supplier contact. ID={}", p.getPackageid());
        Logger.debug("Executing query: {}", sql);

        int numAffectedRows = 0;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getPkgname());
            stmt.setDate(2, java.sql.Date.valueOf(p.getPkgstartdate().get()));
            stmt.setDate(3, java.sql.Date.valueOf(p.getPkgenddate().get()));
            stmt.setString(4, p.getPkgdesc());
            stmt.setInt(5, p.getPkgbaseprice());
            stmt.setInt(6, p.getPkgagencycommission());
            stmt.setInt(7, p.getPackageid());

            numAffectedRows = stmt.executeUpdate();

            if (numAffectedRows > 0) {
                Logger.info("Supplier contact updated successfully. ID={}", p.getPackageid());
            } else {
                Logger.warn("No supplier contact updated. Possibly invalid ID: {}", p.getPackageid());
            }
        } catch (SQLException e) {
            Logger.error(e, "Error updating supplier contact. ID={}", p.getPackageid());
        }
        return numAffectedRows;
    }
}
