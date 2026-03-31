package com.addressbook.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddressBookManager {
    private Map<String, AddressBook> addressBooks;

    public AddressBookManager() {
        this.addressBooks = new HashMap<>();
    }

    public void addAddressBook(String name) {
        if (addressBooks.containsKey(name)) {
            System.out.println("Address Book with name '" + name + "' already exists!");
        } else {
            addressBooks.put(name, new AddressBook());
            System.out.println("Address Book '" + name + "' created successfully!");
        }
    }

    public AddressBook getAddressBook(String name) {
        return addressBooks.get(name);
    }

    public Map<String, AddressBook> getAllAddressBooks() {
        return addressBooks;
    }

    public void displayAllAddressBooks() {
        if (addressBooks.isEmpty()) {
            System.out.println("No address books found!");
        } else {
            System.out.println("\nAvailable Address Books:");
            for (String name : addressBooks.keySet()) {
                System.out.println("- " + name);
            }
        }
    }

    public boolean hasAddressBook(String name) {
        return addressBooks.containsKey(name);
    }

    public List<Contact> searchByCity(String city) {
        return addressBooks.values().stream()
            .flatMap(book -> book.getContacts().stream())
            .filter(contact -> contact.getCity().equalsIgnoreCase(city))
            .collect(Collectors.toList());
    }

    public List<Contact> searchByState(String state) {
        return addressBooks.values().stream()
            .flatMap(book -> book.getContacts().stream())
            .filter(contact -> contact.getState().equalsIgnoreCase(state))
            .collect(Collectors.toList());
    }

    public Map<String, List<Contact>> getPersonsByCity() {
        return addressBooks.values().stream()
            .flatMap(book -> book.getContacts().stream())
            .collect(Collectors.groupingBy(Contact::getCity));
    }

    public Map<String, List<Contact>> getPersonsByState() {
        return addressBooks.values().stream()
            .flatMap(book -> book.getContacts().stream())
            .collect(Collectors.groupingBy(Contact::getState));
    }

    public void displayPersonsByCity() {
        Map<String, List<Contact>> cityMap = getPersonsByCity();
        
        if (cityMap.isEmpty()) {
            System.out.println("No contacts found!");
        } else {
            System.out.println("\n=== Persons Grouped by City ===");
            cityMap.forEach((city, contacts) -> {
                System.out.println("\nCity: " + city);
                for (int i = 0; i < contacts.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + contacts.get(i).getFirstName() + " " + contacts.get(i).getLastName());
                }
            });
        }
    }

    public void displayPersonsByState() {
        Map<String, List<Contact>> stateMap = getPersonsByState();
        
        if (stateMap.isEmpty()) {
            System.out.println("No contacts found!");
        } else {
            System.out.println("\n=== Persons Grouped by State ===");
            stateMap.forEach((state, contacts) -> {
                System.out.println("\nState: " + state);
                for (int i = 0; i < contacts.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + contacts.get(i).getFirstName() + " " + contacts.get(i).getLastName());
                }
            });
        }
    }

    public Map<String, Long> getCountByCity() {
        return addressBooks.values().stream()
            .flatMap(book -> book.getContacts().stream())
            .collect(Collectors.groupingBy(Contact::getCity, Collectors.counting()));
    }

    public Map<String, Long> getCountByState() {
        return addressBooks.values().stream()
            .flatMap(book -> book.getContacts().stream())
            .collect(Collectors.groupingBy(Contact::getState, Collectors.counting()));
    }

    public void displayCountByCity() {
        Map<String, Long> cityCount = getCountByCity();
        
        if (cityCount.isEmpty()) {
            System.out.println("No contacts found!");
        } else {
            System.out.println("\n=== Count of Persons by City ===");
            cityCount.forEach((city, count) -> 
                System.out.println(city + ": " + count + " person(s)")
            );
        }
    }

    public void displayCountByState() {
        Map<String, Long> stateCount = getCountByState();
        
        if (stateCount.isEmpty()) {
            System.out.println("No contacts found!");
        } else {
            System.out.println("\n=== Count of Persons by State ===");
            stateCount.forEach((state, count) -> 
                System.out.println(state + ": " + count + " person(s)")
            );
        }
    }

    public void sortContactsByName(String bookName) {
        AddressBook book = addressBooks.get(bookName);
        if (book != null) {
            book.displaySortedByName();
        } else {
            System.out.println("Address Book not found!");
        }
    }

    public void sortContactsByCity(String bookName) {
        AddressBook book = addressBooks.get(bookName);
        if (book != null) {
            book.displaySortedByCity();
        } else {
            System.out.println("Address Book not found!");
        }
    }

    public void sortContactsByState(String bookName) {
        AddressBook book = addressBooks.get(bookName);
        if (book != null) {
            book.displaySortedByState();
        } else {
            System.out.println("Address Book not found!");
        }
    }

    public void sortContactsByZip(String bookName) {
        AddressBook book = addressBooks.get(bookName);
        if (book != null) {
            book.displaySortedByZip();
        } else {
            System.out.println("Address Book not found!");
        }
    }

    public void writeToFile(String bookName, String fileName) {
        AddressBook book = addressBooks.get(bookName);
        if (book != null) {
            AddressBookFileIO.writeToFile(fileName, book);
        } else {
            System.out.println("Address Book not found!");
        }
    }

    public void readFromFile(String bookName, String fileName) {
        AddressBook book = AddressBookFileIO.readFromFile(fileName);
        if (book != null && !book.getContacts().isEmpty()) {
            addressBooks.put(bookName, book);
            System.out.println("Address Book '" + bookName + "' loaded from file successfully!");
        }
    }

    public void writeToCSV(String bookName, String fileName) {
        AddressBook book = addressBooks.get(bookName);
        if (book != null) {
            AddressBookCSVIO.writeToCSV(fileName, book);
        } else {
            System.out.println("Address Book not found!");
        }
    }

    public void readFromCSV(String bookName, String fileName) {
        AddressBook book = AddressBookCSVIO.readFromCSV(fileName);
        if (book != null && !book.getContacts().isEmpty()) {
            addressBooks.put(bookName, book);
            System.out.println("Address Book '" + bookName + "' loaded from CSV file successfully!");
        }
    }

    public void writeToJSON(String bookName, String fileName) {
        AddressBook book = addressBooks.get(bookName);
        if (book != null) {
            AddressBookJSONIO.writeToJSON(fileName, book);
        } else {
            System.out.println("Address Book not found!");
        }
    }

    public void readFromJSON(String bookName, String fileName) {
        AddressBook book = AddressBookJSONIO.readFromJSON(fileName);
        if (book != null && !book.getContacts().isEmpty()) {
            addressBooks.put(bookName, book);
            System.out.println("Address Book '" + bookName + "' loaded from JSON file successfully!");
        }
    }

    public void loadFromDatabase(String bookName) {
        AddressBookDBService dbService = new AddressBookDBService();
        AddressBook book = dbService.retrieveAddressBook();
        
        if (book != null && !book.getContacts().isEmpty()) {
            addressBooks.put(bookName, book);
            System.out.println("Address Book '" + bookName + "' loaded from database successfully!");
        } else {
            System.out.println("No contacts found in database.");
        }
    }

    public void updateContactInDB(String bookName, String firstName, String lastName, Contact updatedContact) {
        AddressBook book = addressBooks.get(bookName);
        if (book == null) {
            System.out.println("Address Book not found!");
            return;
        }

        // Update in memory
        boolean updated = book.editContact(firstName, lastName, updatedContact);
        
        if (updated) {
            // Sync with database
            AddressBookDBService dbService = new AddressBookDBService();
            boolean dbUpdated = dbService.updateContact(firstName, lastName, updatedContact);
            
            if (dbUpdated) {
                System.out.println("Contact updated and synced with database successfully!");
            } else {
                System.out.println("Failed to sync with database.");
            }
        }
    }

    public boolean isContactInSync(String firstName, String lastName, Contact memoryContact) {
        AddressBookDBService dbService = new AddressBookDBService();
        Contact dbContact = dbService.getContactByName(firstName, lastName);
        
        if (dbContact == null) {
            System.out.println("Contact not found in database.");
            return false;
        }
        
        boolean isInSync = memoryContact.equals(dbContact) &&
                          memoryContact.getAddress().equals(dbContact.getAddress()) &&
                          memoryContact.getCity().equals(dbContact.getCity()) &&
                          memoryContact.getState().equals(dbContact.getState()) &&
                          memoryContact.getZip().equals(dbContact.getZip()) &&
                          memoryContact.getPhoneNumber().equals(dbContact.getPhoneNumber()) &&
                          memoryContact.getEmail().equals(dbContact.getEmail());
        
        if (isInSync) {
            System.out.println("Contact is in sync with database.");
        } else {
            System.out.println("Contact is NOT in sync with database.");
        }
        
        return isInSync;
    }

    public void getContactsByDateRange(String startDate, String endDate) {
        AddressBookDBService dbService = new AddressBookDBService();
        List<Contact> contacts = dbService.getContactsByDateRange(startDate, endDate);
        
        if (contacts.isEmpty()) {
            System.out.println("No contacts found for the specified date range.");
        } else {
            System.out.println("\nContacts added between " + startDate + " and " + endDate + ":");
            for (int i = 0; i < contacts.size(); i++) {
                System.out.println("\n--- Contact " + (i + 1) + " ---");
                System.out.println(contacts.get(i));
            }
        }
    }

    public void displayCountByCityFromDB() {
        AddressBookDBService dbService = new AddressBookDBService();
        Map<String, Long> cityCount = dbService.getCountByCity();
        
        if (cityCount.isEmpty()) {
            System.out.println("No contacts found in database!");
        } else {
            System.out.println("\n=== Count of Persons by City (From Database) ===");
            cityCount.forEach((city, count) -> 
                System.out.println(city + ": " + count + " person(s)")
            );
        }
    }

    public void displayCountByStateFromDB() {
        AddressBookDBService dbService = new AddressBookDBService();
        Map<String, Long> stateCount = dbService.getCountByState();
        
        if (stateCount.isEmpty()) {
            System.out.println("No contacts found in database!");
        } else {
            System.out.println("\n=== Count of Persons by State (From Database) ===");
            stateCount.forEach((state, count) -> 
                System.out.println(state + ": " + count + " person(s)")
            );
        }
    }

    public void addContactToDB(String bookName, Contact contact) {
        AddressBookDBService dbService = new AddressBookDBService();
        
        // Add to database
        boolean success = dbService.addContact(contact);
        
        // If successful, also add to in-memory address book
        if (success) {
            AddressBook book = addressBooks.get(bookName);
            if (book != null) {
                book.getContacts().add(contact);
                System.out.println("Contact also added to in-memory address book.");
            } else {
                System.out.println("Warning: Address book '" + bookName + "' not found in memory.");
            }
        }
    }

    public void addMultipleContactsToDB(String bookName, List<Contact> contacts) {
        AddressBookDBService dbService = new AddressBookDBService();
        
        // Add multiple contacts to database using threads
        int successCount = dbService.addMultipleContacts(contacts);
        
        // If successful, also add to in-memory address book
        if (successCount > 0) {
            AddressBook book = addressBooks.get(bookName);
            if (book != null) {
                book.getContacts().addAll(contacts);
                System.out.println(successCount + " contact(s) also added to in-memory address book.");
            } else {
                System.out.println("Warning: Address book '" + bookName + "' not found in memory.");
            }
        }
    }
}

