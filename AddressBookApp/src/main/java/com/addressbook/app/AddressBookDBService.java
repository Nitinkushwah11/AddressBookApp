package com.addressbook.app;

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
                "email VARCHAR(100)" +
                ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(createTableSQL);
            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }
}