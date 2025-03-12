package org.example.travelexpertdesktopapplication.dao;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.travelexpertdesktopapplication.models.Packages;
import org.tinylog.Logger;

import java.sql.*;

import static org.example.travelexpertdesktopapplication.dao.DatabaseManager.getConnection;

public class PackagesDAO {

    public static ObservableList<Packages> getPackagesList() throws SQLException{
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
                                resultSet.getDate("pkgstartdate").toLocalDate() : null),
                        new SimpleObjectProperty<>(resultSet.getDate("pkgenddate") != null ?
                                resultSet.getDate("pkgenddate").toLocalDate() : null),
                        new SimpleStringProperty(resultSet.getString("pkgdesc")),
                        new SimpleIntegerProperty(resultSet.getInt("pkgbaseprice")),
                        new SimpleIntegerProperty(resultSet.getInt("pkgagencycommission"))
                );
                packageList.add(packages);
            }
        } catch (SQLException e) {
            Logger.debug("Database error: {}", e);
            throw e;
        }
        return packageList;
    }

    public static int deletePackage(int packageID) throws SQLException {
        String sql = "DELETE FROM packages WHERE packageid = ?";
        Logger.debug("Deleting package with ID: {}", packageID);
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
            throw e;
        }
        return affectedRows;
    }

    public static int addPackage(Packages p) throws SQLException {
        int generatedId = -1; // Default value if insertion fails
        Logger.debug("Adding new package: {}", p);

        String sql = "INSERT INTO packages (pkgName, pkgstartdate, pkgenddate, pkgdesc, " +
                "pkgbaseprice, pkgagencycommission) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getPkgname());
            stmt.setDate(2, java.sql.Date.valueOf(p.getPkgstartdate().get()));
            stmt.setDate(3, java.sql.Date.valueOf(p.getPkgenddate().get()));
            stmt.setString(4, p.getPkgdesc());
            stmt.setInt(5, p.getPkgbaseprice());
            stmt.setInt(6, p.getPkgagencycommission());

            Logger.debug("Executing query: {}", sql);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                        Logger.info("Package added successfully with ID: {}", generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            Logger.error(e, "Error adding Package.");
            throw e;
        }
        return generatedId;
    }

    public static int updatePackage(Packages p) throws SQLException {
        String sql = "UPDATE packages SET pkgName = ?, pkgstartdate = ?, pkgenddate = ?, " +
                "pkgdesc = ?, pkgbaseprice = ?, pkgagencycommission = ? " +
                "WHERE packageid = ?";

        Logger.debug("Updating package. ID={}", p.getPackageid());
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
                Logger.info("Package updated successfully. ID={}", p.getPackageid());
            } else {
                Logger.warn("No Package updated. Possibly invalid ID: {}", p.getPackageid());
            }
        } catch (SQLException e) {
            Logger.error(e, "Error updating Package. ID={}", p.getPackageid());
            throw e;
        }
        return numAffectedRows;
    }
}
