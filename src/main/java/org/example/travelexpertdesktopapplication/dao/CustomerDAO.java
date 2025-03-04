package org.example.travelexpertdesktopapplication.dao;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.travelexpertdesktopapplication.models.Agent;
import org.example.travelexpertdesktopapplication.models.Customer;
import org.example.travelexpertdesktopapplication.models.SupplierContacts;
import org.tinylog.Logger;

import java.sql.*;

public class CustomerDAO {

    /**
     * get customer list from database
     * @return array of customer
     */
    public static ObservableList<Customer> getCustomerList() {
        ObservableList<Customer> customersList = FXCollections.observableArrayList();
        String query = "SELECT * FROM customers;";
        Logger.debug("Fetching customers from the database.");

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            Logger.debug("Executing query: {}", query);
            while (rs.next()) {
                Customer customers = new Customer(
                        new SimpleIntegerProperty(rs.getInt("customerid")),
                        new SimpleStringProperty(rs.getString("custfirstname")),
                        new SimpleStringProperty(rs.getString("custlastname")),
                        new SimpleStringProperty(rs.getString("custaddress")),
                        new SimpleStringProperty(rs.getString("custcity")),
                        new SimpleStringProperty(rs.getString("custprov")),
                        new SimpleStringProperty(rs.getString("custpostal")),
                        new SimpleStringProperty(rs.getString("custcountry")),
                        new SimpleStringProperty(rs.getString("custhomephone")),
                        new SimpleStringProperty(rs.getString("custbusphone")),
                        new SimpleStringProperty(rs.getString("custemail")),
                        new SimpleIntegerProperty(rs.getInt("agentid"))
                );
                customersList.add(customers);
            }
            Logger.info("Retrieved {} customers.", customersList.size());
        } catch (SQLException e) {
            Logger.error(e, "Error retrieving customers.");
        }
        return customersList;
    }

    public static ObservableList<Customer> getAgentCustomers(int agentID) {
        ObservableList<Customer> agentCustomersList = FXCollections.observableArrayList();
        String query = "SELECT * FROM customers WHERE agentid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, agentID);  // Set the parameter

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer(
                            new SimpleIntegerProperty(rs.getInt("customerid")),
                            new SimpleStringProperty(rs.getString("custfirstname")),
                            new SimpleStringProperty(rs.getString("custlastname")),
                            new SimpleStringProperty(rs.getString("custaddress")),
                            new SimpleStringProperty(rs.getString("custcity")),
                            new SimpleStringProperty(rs.getString("custprov")),
                            new SimpleStringProperty(rs.getString("custpostal")),
                            new SimpleStringProperty(rs.getString("custcountry")),
                            new SimpleStringProperty(rs.getString("custhomephone")),
                            new SimpleStringProperty(rs.getString("custbusphone")),
                            new SimpleStringProperty(rs.getString("custemail")),
                            new SimpleIntegerProperty(rs.getInt("agentid"))
                    );
                    agentCustomersList.add(customer);
                }
            }
        } catch (SQLException e) {
            Logger.error(e, "Error retrieving customers.");
        }
        return agentCustomersList;
    }

    public static int updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET custfirstname = ?, custlastname = ?, custaddress = ?, custcity = ?, " +
                "custprov = ?, custpostal = ?, custcountry = ?, custhomephone = ?, custbusphone = ?, " +
                "custemail = ?, agentid = ? WHERE customerid = ?";

        Logger.debug("Updating customer. ID={}", customer.getCustomerid());
        Logger.debug("Executing query: {}", sql);

        int numAffectedRows = 0;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getCustfirstname());
            stmt.setString(2, customer.getCustlastname());
            stmt.setString(3, customer.getCustaddress());
            stmt.setString(4, customer.getCustcity());
            stmt.setString(5, customer.getCustprov());
            stmt.setString(6, customer.getCustpostal());
            stmt.setString(7, customer.getCustcountry());
            stmt.setString(8, customer.getCusthomephone());
            stmt.setString(9, customer.getCustbusphone());
            stmt.setString(10, customer.getCustemail());
            stmt.setInt(11, customer.getAgentid());
            stmt.setInt(12, customer.getCustomerid());

            numAffectedRows = stmt.executeUpdate();

            if (numAffectedRows > 0) {
                Logger.info("Customer updated successfully. ID={}", customer.getCustomerid());
            } else {
                Logger.warn("No customer updated. Possibly invalid ID: {}", customer.getCustomerid());
            }

        } catch (SQLException e) {
            Logger.error(e, "Error updating customer. ID={}", customer.getCustomerid());
        }

        return numAffectedRows;
    }


}
