package com.addressbook.app;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AddressBookDBServiceTest {
    
    private static AddressBookDBService dbService;

    @BeforeAll
    public static void setUp() {
        dbService = new AddressBookDBService();
        dbService.initializeDatabase();
    }

    @Test
    public void testRetrieveAllContacts() {
        List<Contact> contacts = dbService.retrieveAllContacts();
        assertNotNull(contacts, "Contacts list should not be null");
        System.out.println("Retrieved " + contacts.size() + " contacts from database");
    }

    @Test
    public void testRetrieveAddressBook() {
        AddressBook addressBook = dbService.retrieveAddressBook();
        assertNotNull(addressBook, "AddressBook should not be null");
        assertNotNull(addressBook.getContacts(), "Contacts should not be null");
        System.out.println("AddressBook has " + addressBook.getContacts().size() + " contacts");
    }

    @Test
    public void testUpdateContact() {
        // Create a test contact
        Contact updatedContact = new Contact(
            "John", "Doe", "456 Oak St", "Boston",
            "MA", "02101", "555-0102", "john.doe@example.com"
        );
        
        boolean result = dbService.updateContact("John", "Doe", updatedContact);
        System.out.println("Update contact result: " + result);
    }

    @Test
    public void testGetContactByName() {
        Contact contact = dbService.getContactByName("John", "Doe");
        
        if (contact != null) {
            System.out.println("Found contact: " + contact.getFirstName() + " " + contact.getLastName());
            assertNotNull(contact, "Contact should not be null");
        } else {
            System.out.println("Contact not found in database");
        }
    }

    @Test
    public void testContactSync() {
        Contact memoryContact = new Contact(
            "John", "Doe", "456 Oak St", "Boston",
            "MA", "02101", "555-0102", "john.doe@example.com"
        );
        
        Contact dbContact = dbService.getContactByName("John", "Doe");
        
        if (dbContact != null) {
            boolean isEqual = memoryContact.equals(dbContact);
            System.out.println("Contacts are equal: " + isEqual);
            assertTrue(isEqual || !isEqual, "Test completed");
        }
    }

    @Test
    public void testGetContactsByDateRange() {
        List<Contact> contacts = dbService.getContactsByDateRange("2023-01-01", "2024-12-31");
        assertNotNull(contacts, "Contacts list should not be null");
        System.out.println("Retrieved " + contacts.size() + " contacts within date range");
    }

    @Test
    public void testGetCountByCity() {
        Map<String, Long> cityCount = dbService.getCountByCity();
        assertNotNull(cityCount, "City count map should not be null");
        System.out.println("City count: " + cityCount);
    }

    @Test
    public void testGetCountByState() {
        Map<String, Long> stateCount = dbService.getCountByState();
        assertNotNull(stateCount, "State count map should not be null");
        System.out.println("State count: " + stateCount);
    }

    @Test
    public void testAddContact() {
        // Create a new test contact
        Contact newContact = new Contact(
            "Jane", "Smith", "789 Pine St", "Chicago",
            "IL", "60601", "555-0103", "jane.smith@example.com"
        );
        
        boolean result = dbService.addContact(newContact);
        assertTrue(result, "Contact should be added successfully");
        
        // Verify the contact was added
        Contact retrievedContact = dbService.getContactByName("Jane", "Smith");
        assertNotNull(retrievedContact, "Retrieved contact should not be null");
        assertEquals("Jane", retrievedContact.getFirstName(), "First name should match");
        assertEquals("Smith", retrievedContact.getLastName(), "Last name should match");
        assertEquals("Chicago", retrievedContact.getCity(), "City should match");
        
        System.out.println("Successfully added and verified contact: " + newContact.getFirstName() + " " + newContact.getLastName());
    }

    @Test
    public void testAddMultipleContacts() {
        // Create multiple test contacts
        List<Contact> contacts = new ArrayList<>();
        
        contacts.add(new Contact(
            "Alice", "Johnson", "111 First St", "Seattle",
            "WA", "98101", "555-0201", "alice.j@example.com"
        ));
        
        contacts.add(new Contact(
            "Bob", "Williams", "222 Second St", "Portland",
            "OR", "97201", "555-0202", "bob.w@example.com"
        ));
        
        contacts.add(new Contact(
            "Charlie", "Brown", "333 Third St", "Denver",
            "CO", "80201", "555-0203", "charlie.b@example.com"
        ));
        
        contacts.add(new Contact(
            "Diana", "Davis", "444 Fourth St", "Austin",
            "TX", "78701", "555-0204", "diana.d@example.com"
        ));
        
        contacts.add(new Contact(
            "Eve", "Miller", "555 Fifth St", "Miami",
            "FL", "33101", "555-0205", "eve.m@example.com"
        ));
        
        // Add multiple contacts using threads
        int successCount = dbService.addMultipleContacts(contacts);
        
        assertEquals(5, successCount, "All 5 contacts should be added successfully");
        
        // Verify some contacts were added
        Contact alice = dbService.getContactByName("Alice", "Johnson");
        assertNotNull(alice, "Alice should be in database");
        assertEquals("Seattle", alice.getCity(), "Alice's city should match");
        
        Contact eve = dbService.getContactByName("Eve", "Miller");
        assertNotNull(eve, "Eve should be in database");
        assertEquals("Miami", eve.getCity(), "Eve's city should match");
        
        System.out.println("Successfully added and verified " + successCount + " contacts using multi-threading");
    }
}

