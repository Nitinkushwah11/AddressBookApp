package com.addressbookapp.util;

import com.addressbookapp.model.Contact;
import com.addressbookapp.service.AddressBook;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class AddressBookMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Maintain multiple Address Books
        Map<String, AddressBook> addressBooks = new HashMap<>();

        System.out.println("Welcome to Multi Address Book Program");

        while(true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Create New Address Book");
            System.out.println("2. Select Address Book");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch(choice) {
                case 1: // Create new Address Book
                    System.out.print("Enter new Address Book Name: ");
                    String bookName = sc.nextLine();
                    if(addressBooks.containsKey(bookName)) {
                        System.out.println("Address Book with this name already exists!");
                    } else {
                        addressBooks.put(bookName, new AddressBook());
                        System.out.println("Address Book '" + bookName + "' created successfully!");
                    }
                    break;

                case 2: // Select Address Book
                    System.out.print("Enter Address Book Name to use: ");
                    String selectedBook = sc.nextLine();

                    AddressBook currentBook = addressBooks.get(selectedBook);
                    if(currentBook == null) {
                        System.out.println("Address Book not found!");
                        break;
                    }

                    // Operations on selected Address Book
                    while(true) {
                        System.out.println("\nOperations on '" + selectedBook + "':");
                        System.out.println("1. Add Contacts");
                        System.out.println("2. View Contacts");
                        System.out.println("3. Edit Contact");
                        System.out.println("4. Delete Contact");
                        System.out.println("5. Back to Main Menu");
                        System.out.print("Enter your choice: ");
                        int op = sc.nextInt();
                        sc.nextLine(); // consume newline

                        switch(op) {
                            case 1: // Add multiple contacts
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
                                    currentBook.addContact(new Contact(name, phone, email));
                                }
                                break;

                            case 2: // View contacts
                                currentBook.displayContacts();
                                break;

                            case 3: // Edit contact
                                System.out.print("Enter Name to edit: ");
                                String editName = sc.nextLine();
                                System.out.print("Enter new Name: ");
                                String newName = sc.nextLine();
                                System.out.print("Enter new Phone: ");
                                String newPhone = sc.nextLine();
                                System.out.print("Enter new Email: ");
                                String newEmail = sc.nextLine();
                                currentBook.editContact(editName, new Contact(newName, newPhone, newEmail));
                                break;

                            case 4: // Delete contact
                                System.out.print("Enter Name to delete: ");
                                String delName = sc.nextLine();
                                currentBook.deleteContact(delName);
                                break;

                            case 5: // Back
                                break;

                            default:
                                System.out.println("Invalid choice!");
                        }
                        if(op == 5) break;
                    }
                    break;

                case 3: // Exit
                    System.out.println("Exiting program...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}