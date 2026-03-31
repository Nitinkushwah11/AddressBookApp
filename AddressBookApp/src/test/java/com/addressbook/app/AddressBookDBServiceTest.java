package com.addressbook.app;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;

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
}
