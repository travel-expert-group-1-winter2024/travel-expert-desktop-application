package org.example.travelexpertdesktopapplication.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.travelexpertdesktopapplication.models.BookingDetails;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.travelexpertdesktopapplication.dao.DatabaseManager.getConnection;

public class BookingDetailsDAO {
    public static ObservableList<BookingDetails> getBookingDetailsList() throws SQLException{
        ObservableList<BookingDetails> bookingList = FXCollections.observableArrayList();

        String query = "SELECT * FROM BookingDetailsView";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                bookingList.add(new BookingDetails(
                        rs.getInt("bookingdetailid"),
                        rs.getString("itineraryno"),
                        rs.getDate("tripstart").toLocalDate(),
                        rs.getDate("tripend").toLocalDate(),
                        rs.getString("description"),
                        rs.getString("destination"),
                        rs.getDouble("baseprice"),
                        rs.getDouble("agencycommission"),
                        rs.getInt("bookingid"),
                        rs.getInt("agentid"),
                        rs.getString("region"),
                        rs.getString("class"),
                        rs.getString("fee"),
                        rs.getString("product"),
                        rs.getString("supplier")
                ));
            }
        } catch (SQLException e) {
            throw e;
        }

        return bookingList;
    }
}
