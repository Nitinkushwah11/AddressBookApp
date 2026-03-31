package com.addressbook.app;

import java.util.List;
import java.util.Scanner;

public class AddressBookMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AddressBookManager manager = new AddressBookManager();
        boolean exit = false;

        System.out.println("Welcome to Address Book System!");

        while (!exit) {
            System.out.println("\n1. Create New Address Book");
            System.out.println("2. Select Address Book");
            System.out.println("3. Display All Address Books");
            System.out.println("4. Search Person by City");
            System.out.println("5. Search Person by State");
            System.out.println("6. View Persons by City");
            System.out.println("7. View Persons by State");
            System.out.println("8. Count Contacts by City");
            System.out.println("9. Count Contacts by State");
            System.out.println("10. Sort Contacts by Name");
            System.out.println("11. Sort Contacts by City");
            System.out.println("12. Sort Contacts by State");
            System.out.println("13. Sort Contacts by Zip");
            System.out.println("14. Write Address Book to File");
            System.out.println("15. Read Address Book from File");
            System.out.println("16. Write Address Book to CSV");
            System.out.println("17. Read Address Book from CSV");
            System.out.println("18. Write Address Book to JSON");
            System.out.println("19. Read Address Book from JSON");
            System.out.println("20. Load Address Book from Database");
            System.out.println("21. Add Contact to Database");
            System.out.println("22. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createAddressBook(scanner, manager);
                    break;
                case 2:
                    selectAddressBook(scanner, manager);
                    break;
                case 3:
                    manager.displayAllAddressBooks();
                    break;
                case 4:
                    searchByCity(scanner, manager);
                    break;
                case 5:
                    searchByState(scanner, manager);
                    break;
                case 6:
                    manager.displayPersonsByCity();
                    break;
                case 7:
                    manager.displayPersonsByState();
                    break;
                case 8:
                    manager.displayCountByCity();
                    break;
                case 9:
                    manager.displayCountByState();
                    break;
                case 10:
                    sortByName(scanner, manager);
                    break;
                case 11:
                    sortByCity(scanner, manager);
                    break;
                case 12:
                    sortByState(scanner, manager);
                    break;
                case 13:
                    sortByZip(scanner, manager);
                    break;
                case 14:
                    writeToFile(scanner, manager);
                    break;
                case 15:
                    readFromFile(scanner, manager);
                    break;
                case 16:
                    writeToCSV(scanner, manager);
                    break;
                case 17:
                    readFromCSV(scanner, manager);
                    break;
                case 18:
                    writeToJSON(scanner, manager);
                    break;
                case 19:
                    readFromJSON(scanner, manager);
                    break;
                case 20:
                    loadFromDatabase(scanner, manager);
                    break;
                case 21:
                    addContactToDatabase(scanner, manager);
                    break;
                case 22:
                    exit = true;
                    System.out.println("Exiting Address Book System...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }

        scanner.close();
    }

    private static void createAddressBook(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter Address Book Name: ");
        String name = scanner.nextLine();
        manager.addAddressBook(name);
    }

    private static void selectAddressBook(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter Address Book Name: ");
        String name = scanner.nextLine();

        if (!manager.hasAddressBook(name)) {
            System.out.println("Address Book '" + name + "' not found!");
            return;
        }

        AddressBook addressBook = manager.getAddressBook(name);
        manageAddressBook(scanner, addressBook, name);
    }

    private static void manageAddressBook(Scanner scanner, AddressBook addressBook, String bookName) {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== Address Book: " + bookName + " ===");
            System.out.println("1. Add Contact");
            System.out.println("2. Edit Contact");
            System.out.println("3. Delete Contact");
            System.out.println("4. Display Contacts");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addContact(scanner, addressBook);
                    break;
                case 2:
                    editContact(scanner, addressBook);
                    break;
                case 3:
                    deleteContact(scanner, addressBook);
                    break;
                case 4:
                    addressBook.displayContact();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void addContact(Scanner scanner, AddressBook addressBook) {
        System.out.println("\nEnter contact details:");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.print("City: ");
        String city = scanner.nextLine();

        System.out.print("State: ");
        String state = scanner.nextLine();

        System.out.print("Zip: ");
        String zip = scanner.nextLine();

        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        Contact contact = new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
        addressBook.addContact(contact);
    }

    private static void editContact(Scanner scanner, AddressBook addressBook) {
        System.out.println("\nEnter name of contact to edit:");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.println("\nEnter updated contact details:");
        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.print("City: ");
        String city = scanner.nextLine();

        System.out.print("State: ");
        String state = scanner.nextLine();

        System.out.print("Zip: ");
        String zip = scanner.nextLine();

        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        Contact updatedContact = new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
        addressBook.editContact(firstName, lastName, updatedContact);
    }

    private static void deleteContact(Scanner scanner, AddressBook addressBook) {
        System.out.println("\nEnter name of contact to delete:");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        addressBook.deleteContact(firstName, lastName);
    }

    private static void searchByCity(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter City: ");
        String city = scanner.nextLine();

        List<Contact> results = manager.searchByCity(city);
        
        if (results.isEmpty()) {
            System.out.println("No contacts found in city: " + city);
        } else {
            System.out.println("\nContacts found in " + city + ":");
            for (int i = 0; i < results.size(); i++) {
                System.out.println("\n--- Contact " + (i + 1) + " ---");
                System.out.println(results.get(i));
            }
        }
    }

    private static void searchByState(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter State: ");
        String state = scanner.nextLine();

        List<Contact> results = manager.searchByState(state);
        
        if (results.isEmpty()) {
            System.out.println("No contacts found in state: " + state);
        } else {
            System.out.println("\nContacts found in " + state + ":");
            for (int i = 0; i < results.size(); i++) {
                System.out.println("\n--- Contact " + (i + 1) + " ---");
                System.out.println(results.get(i));
            }
        }
    }

    private static void sortByName(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter Address Book Name: ");
        String bookName = scanner.nextLine();

        if (!manager.hasAddressBook(bookName)) {
            System.out.println("Address Book '" + bookName + "' does not exist!");
            return;
        }

        manager.sortContactsByName(bookName);
    }

    private static void sortByCity(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter Address Book Name: ");
        String bookName = scanner.nextLine();

        if (!manager.hasAddressBook(bookName)) {
            System.out.println("Address Book '" + bookName + "' does not exist!");
            return;
        }

        manager.sortContactsByCity(bookName);
    }

    private static void sortByState(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter Address Book Name: ");
        String bookName = scanner.nextLine();

        if (!manager.hasAddressBook(bookName)) {
            System.out.println("Address Book '" + bookName + "' does not exist!");
            return;
        }

        manager.sortContactsByState(bookName);
    }

    private static void sortByZip(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter Address Book Name: ");
        String bookName = scanner.nextLine();

        if (!manager.hasAddressBook(bookName)) {
            System.out.println("Address Book '" + bookName + "' does not exist!");
            return;
        }

        manager.sortContactsByZip(bookName);
    }

    private static void writeToFile(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter Address Book Name: ");
        String bookName = scanner.nextLine();

        if (!manager.hasAddressBook(bookName)) {
            System.out.println("Address Book '" + bookName + "' does not exist!");
            return;
        }

        System.out.print("Enter File Name: ");
        String fileName = scanner.nextLine();

        manager.writeToFile(bookName, fileName);
    }

    private static void readFromFile(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter Address Book Name to create/overwrite: ");
        String bookName = scanner.nextLine();

        System.out.print("Enter File Name: ");
        String fileName = scanner.nextLine();

        manager.readFromFile(bookName, fileName);
    }

    private static void writeToCSV(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter Address Book Name: ");
        String bookName = scanner.nextLine();

        if (!manager.hasAddressBook(bookName)) {
            System.out.println("Address Book '" + bookName + "' does not exist!");
            return;
        }

        System.out.print("Enter CSV File Name: ");
        String fileName = scanner.nextLine();

        manager.writeToCSV(bookName, fileName);
    }

    private static void readFromCSV(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter Address Book Name to create/overwrite: ");
        String bookName = scanner.nextLine();

        System.out.print("Enter CSV File Name: ");
        String fileName = scanner.nextLine();

        manager.readFromCSV(bookName, fileName);
    }

    private static void writeToJSON(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter Address Book Name: ");
        String bookName = scanner.nextLine();

        if (!manager.hasAddressBook(bookName)) {
            System.out.println("Address Book '" + bookName + "' does not exist!");
            return;
        }

        System.out.print("Enter JSON File Name: ");
        String fileName = scanner.nextLine();

        manager.writeToJSON(bookName, fileName);
    }

    private static void readFromJSON(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter Address Book Name to create/overwrite: ");
        String bookName = scanner.nextLine();

        System.out.print("Enter JSON File Name: ");
        String fileName = scanner.nextLine();

        manager.readFromJSON(bookName, fileName);
    }

    private static void loadFromDatabase(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter Address Book Name to create/overwrite: ");
        String bookName = scanner.nextLine();

        manager.loadFromDatabase(bookName);
    }

    private static void addContactToDatabase(Scanner scanner, AddressBookManager manager) {
        System.out.print("\nEnter Address Book Name (for in-memory sync): ");
        String bookName = scanner.nextLine();

        // Create the address book if it doesn't exist
        if (!manager.hasAddressBook(bookName)) {
            manager.addAddressBook(bookName);
        }

        System.out.println("\n--- Enter Contact Details ---");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.print("City: ");
        String city = scanner.nextLine();

        System.out.print("State: ");
        String state = scanner.nextLine();

        System.out.print("ZIP: ");
        String zip = scanner.nextLine();

        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        Contact contact = new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
        manager.addContactToDB(bookName, contact);
    }
}

