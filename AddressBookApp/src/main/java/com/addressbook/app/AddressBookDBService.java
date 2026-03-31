package com.addressbook.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

    public Map<String, Long> getCountByCity() {
        Map<String, Long> cityCount = new HashMap<>();
        String query = "SELECT city, COUNT(*) as count FROM contacts GROUP BY city";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                cityCount.put(rs.getString("city"), rs.getLong("count"));
            }
            System.out.println("Retrieved count by city from database.");

        } catch (SQLException e) {
            System.out.println("Error retrieving count by city: " + e.getMessage());
        }

        return cityCount;
    }

    public Map<String, Long> getCountByState() {
        Map<String, Long> stateCount = new HashMap<>();
        String query = "SELECT state, COUNT(*) as count FROM contacts GROUP BY state";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                stateCount.put(rs.getString("state"), rs.getLong("count"));
            }
            System.out.println("Retrieved count by state from database.");

        } catch (SQLException e) {
            System.out.println("Error retrieving count by state: " + e.getMessage());
        }

        return stateCount;
    }

    public boolean addContact(Contact contact) {
        String query = "INSERT INTO contacts (first_name, last_name, address, city, state, zip, phone_number, email, date_added) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURDATE())";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, contact.getFirstName());
            pstmt.setString(2, contact.getLastName());
            pstmt.setString(3, contact.getAddress());
            pstmt.setString(4, contact.getCity());
            pstmt.setString(5, contact.getState());
            pstmt.setString(6, contact.getZip());
            pstmt.setString(7, contact.getPhoneNumber());
            pstmt.setString(8, contact.getEmail());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit(); // Commit transaction
                System.out.println("Contact added successfully to database.");
                return true;
            } else {
                conn.rollback(); // Rollback if no rows affected
                System.out.println("Failed to add contact to database.");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("Error adding contact to database: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                    System.out.println("Transaction rolled back.");
                } catch (SQLException ex) {
                    System.out.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Restore auto-commit
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public int addMultipleContacts(List<Contact> contacts) {
        if (contacts == null || contacts.isEmpty()) {
            System.out.println("No contacts to add!");
            return 0;
        }

        int threadPoolSize = Math.min(contacts.size(), 4); // Use max 4 threads
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        System.out.println("Starting multi-threaded insertion of " + contacts.size() + " contacts using " + threadPoolSize + " threads...");
        long startTime = System.currentTimeMillis();

        // Submit tasks to thread pool
        for (Contact contact : contacts) {
            executor.submit(() -> {
                try {
                    boolean result = addContact(contact);
                    if (result) {
                        successCount.incrementAndGet();
                        System.out.println("Thread " + Thread.currentThread().getName() + 
                                         " successfully added: " + contact.getFirstName() + " " + contact.getLastName());
                    } else {
                        failureCount.incrementAndGet();
                        System.out.println("Thread " + Thread.currentThread().getName() + 
                                         " failed to add: " + contact.getFirstName() + " " + contact.getLastName());
                    }
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                    System.out.println("Thread " + Thread.currentThread().getName() + 
                                     " encountered error: " + e.getMessage());
                }
            });
        }

        // Shutdown executor and wait for completion
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                System.out.println("Warning: Some tasks did not complete within timeout.");
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            System.out.println("Thread pool interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("\n=== Multi-threaded Insertion Complete ===");
        System.out.println("Total contacts: " + contacts.size());
        System.out.println("Successfully added: " + successCount.get());
        System.out.println("Failed: " + failureCount.get());
        System.out.println("Time taken: " + (endTime - startTime) + " ms");

        return successCount.get();
    }
}

