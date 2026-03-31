package com.addressbook.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/addressbook_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public List<Contact> retrieveAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Contact contact = new Contact(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("zip"),
                    rs.getString("phone_number"),
                    rs.getString("email")
                );
                contacts.add(contact);
            }
            System.out.println("Retrieved " + contacts.size() + " contacts from database.");

        } catch (SQLException e) {
            System.out.println("Error retrieving contacts from database: " + e.getMessage());
        }

        return contacts;
    }

    public AddressBook retrieveAddressBook() {
        AddressBook addressBook = new AddressBook();
        List<Contact> contacts = retrieveAllContacts();
        
        for (Contact contact : contacts) {
            addressBook.getContacts().add(contact);
        }
        
        return addressBook;
    }

    public void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS contacts (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "first_name VARCHAR(50) NOT NULL," +
                "last_name VARCHAR(50) NOT NULL," +
                "address VARCHAR(100)," +
                "city VARCHAR(50)," +
                "state VARCHAR(50)," +
                "zip VARCHAR(10)," +
                "phone_number VARCHAR(15)," +
                "email VARCHAR(100)," +
                "date_added DATE NOT NULL" +
                ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(createTableSQL);
            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    public boolean updateContact(String firstName, String lastName, Contact updatedContact) {
        String query = "UPDATE contacts SET first_name = ?, last_name = ?, address = ?, " +
                      "city = ?, state = ?, zip = ?, phone_number = ?, email = ? " +
                      "WHERE first_name = ? AND last_name = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, updatedContact.getFirstName());
            pstmt.setString(2, updatedContact.getLastName());
            pstmt.setString(3, updatedContact.getAddress());
            pstmt.setString(4, updatedContact.getCity());
            pstmt.setString(5, updatedContact.getState());
            pstmt.setString(6, updatedContact.getZip());
            pstmt.setString(7, updatedContact.getPhoneNumber());
            pstmt.setString(8, updatedContact.getEmail());
            pstmt.setString(9, firstName);
            pstmt.setString(10, lastName);

            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Contact updated successfully in database.");
                return true;
            } else {
                System.out.println("No contact found with name: " + firstName + " " + lastName);
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error updating contact in database: " + e.getMessage());
            return false;
        }
    }

    public Contact getContactByName(String firstName, String lastName) {
        String query = "SELECT * FROM contacts WHERE first_name = ? AND last_name = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Contact(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip"),
                        rs.getString("phone_number"),
                        rs.getString("email")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving contact from database: " + e.getMessage());
        }

        return null;
    }

    public List<Contact> getContactsByDateRange(String startDate, String endDate) {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts WHERE date_added BETWEEN ? AND ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Contact contact = new Contact(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip"),
                        rs.getString("phone_number"),
                        rs.getString("email")
                    );
                    contacts.add(contact);
                }
            }

            System.out.println("Retrieved " + contacts.size() + " contacts added between " + startDate + " and " + endDate);

        } catch (SQLException e) {
            System.out.println("Error retrieving contacts by date range: " + e.getMessage());
        }

        return contacts;
    }
}
