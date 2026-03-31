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
}