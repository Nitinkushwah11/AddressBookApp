package com.addressbookapp.util;

import com.addressbookapp.model.Contact;
import com.addressbookapp.service.AddressBook;

import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, AddressBook> addressBooks = new HashMap<>();

        System.out.println("Welcome to Multi Address Book Program");

        while(true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Create New Address Book");
            System.out.println("2. Select Address Book");
            System.out.println("3. Search Person by City/State across Address Books");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1:
                    System.out.print("Enter new Address Book Name: ");
                    String bookName = sc.nextLine();
                    if(addressBooks.containsKey(bookName)) {
                        System.out.println("Address Book with this name already exists!");
                    } else {
                        addressBooks.put(bookName, new AddressBook());
                        System.out.println("Address Book '" + bookName + "' created successfully!");
                    }
                    break;

                case 2:
                    System.out.print("Enter Address Book Name to use: ");
                    String selectedBook = sc.nextLine();

                    AddressBook currentBook = addressBooks.get(selectedBook);
                    if(currentBook == null) {
                        System.out.println("Address Book not found!");
                        break;
                    }

                    while(true) {
                        System.out.println("\nOperations on '" + selectedBook + "':");
                        System.out.println("1. Add Contacts");
                        System.out.println("2. View Contacts");
                        System.out.println("3. Edit Contact");
                        System.out.println("4. Delete Contact");
                        System.out.println("5. Back to Main Menu");
                        System.out.print("Enter your choice: ");
                        int op = sc.nextInt();
                        sc.nextLine();

                        switch(op) {
                            case 1:
                                System.out.print("How many contacts you want to add? ");
                                int n = sc.nextInt();
                                sc.nextLine();
                                for(int i=0; i<n; i++) {
                                    System.out.println("\nEnter details for contact " + (i+1));
                                    System.out.print("Enter Name: ");
                                    String name = sc.nextLine();
                                    System.out.print("Enter Phone: ");
                                    String phone = sc.nextLine();
                                    System.out.print("Enter Email: ");
                                    String email = sc.nextLine();
                                    System.out.print("Enter City: ");
                                    String city = sc.nextLine();
                                    System.out.print("Enter State: ");
                                    String state = sc.nextLine();
                                    currentBook.addContact(new Contact(name, phone, email, city, state));
                                }
                                break;

                            case 2:
                                currentBook.displayContacts();
                                break;

                            case 3:

                                System.out.print("Enter City to search (press Enter to skip): ");
                                String citySearch = sc.nextLine();
                                citySearch = citySearch.isEmpty() ? null : citySearch;

                                System.out.print("Enter State to search (press Enter to skip): ");
                                String stateSearch = sc.nextLine();
                                stateSearch = stateSearch.isEmpty() ? null : stateSearch;

                                System.out.println("\nSearch Results:");

                                String finalCitySearch = citySearch;
                                String finalStateSearch = stateSearch;
                                addressBooks.entrySet()
                                        .stream()
                                        .forEach(entry -> {

                                            String bookNamee = entry.getKey();
                                            AddressBook book = entry.getValue();

                                            List<Contact> results = book.searchByCityOrState(finalCitySearch, finalStateSearch);

                                            if(!results.isEmpty()) {
                                                System.out.println("\nAddress Book: " + bookNamee);
                                                results.forEach(System.out::println);
                                            }

                                        });

                                break;

                            case 4:
                                System.out.print("Enter Name to delete: ");
                                String delName = sc.nextLine();
                                currentBook.deleteContact(delName);
                                break;

                            case 5:
                                break;

                            default:
                                System.out.println("Invalid choice!");
                        }
                        if(op == 5) break;
                    }
                    break;

                case 3:
                    System.out.print("Enter City to search (or press Enter to skip): ");
                    String citySearch = sc.nextLine();
                    citySearch = citySearch.isEmpty() ? null : citySearch;

                    System.out.print("Enter State to search (or press Enter to skip): ");
                    String stateSearch = sc.nextLine();
                    stateSearch = stateSearch.isEmpty() ? null : stateSearch;

                    System.out.println("\nSearch Results:");
                    String finalCitySearch = citySearch;
                    String finalStateSearch = stateSearch;
                    addressBooks.forEach((bookNamee, book) -> {
                        List<Contact> results = book.searchByCityOrState(finalCitySearch, finalStateSearch);
                        if(!results.isEmpty()) {
                            System.out.println("\nAddress Book: " + bookNamee);
                            results.forEach(System.out::println);
                        }
                    });
                    break;

                case 4:
                    System.out.println("Exiting program...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}