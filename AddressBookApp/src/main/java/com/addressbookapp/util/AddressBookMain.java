package com.addressbookapp.util;

import com.addressbookapp.model.Contact;
import com.addressbookapp.service.AddressBook;

import java.util.Scanner;

public class AddressBookMain {



    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();

        // Add initial contact
        System.out.println("Add a contact:");
        Contact contact = createContact(sc);
        addressBook.addContact(contact);

        // Display contacts
        System.out.println("Contacts before edit:");
        addressBook.displayContacts();

        // Edit contact
        System.out.println("\nEnter name of contact to edit:");
        System.out.print("First Name: ");
        String firstName = sc.nextLine();
        System.out.print("Last Name: ");
        String lastName = sc.nextLine();

        System.out.println("Enter updated details:");
        Contact updatedContact = createContact(sc);

        boolean edited = addressBook.editContact(firstName, lastName, updatedContact);

        if (edited) {
            System.out.println("Contact updated successfully!");
        } else {
            System.out.println("Contact not found.");
        }

        // Display contacts after edit
        System.out.println("Contacts after edit:");
        addressBook.displayContacts();
    }

    // Helper method to create a contact from console input
    private static Contact createContact(Scanner sc) {
        System.out.print("Enter First Name: ");
        String firstName = sc.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = sc.nextLine();

        System.out.print("Enter Address: ");
        String address = sc.nextLine();

        System.out.print("Enter City: ");
        String city = sc.nextLine();

        System.out.print("Enter State: ");
        String state = sc.nextLine();

        System.out.print("Enter Zip: ");
        String zip = sc.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        return new Contact(firstName, lastName, address, city, state, zip, phone, email);
    }

    }