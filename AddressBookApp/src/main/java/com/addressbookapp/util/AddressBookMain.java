package com.addressbookapp.util;

import com.addressbookapp.model.Contact;
import com.addressbookapp.service.AddressBook;


import java.util.Scanner;
public class AddressBookMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();

        System.out.println("Welcome to Address Book Program");

        while (true) {
            System.out.println("\n1. Add Contact\n2. View Contacts\n3. Edit Contact\n4. Delete Contact\n5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    addressBook.addContact(new Contact(name, phone, email));
                    break;

                case 2:
                    addressBook.displayContacts();
                    break;

                case 3:
                    System.out.print("Enter Name to edit: ");
                    String editName = sc.nextLine();
                    System.out.print("Enter new Name: ");
                    String newName = sc.nextLine();
                    System.out.print("Enter new Phone: ");
                    String newPhone = sc.nextLine();
                    System.out.print("Enter new Email: ");
                    String newEmail = sc.nextLine();
                    addressBook.editContact(editName, new Contact(newName, newPhone, newEmail));
                    break;

                case 4:
                    System.out.print("Enter Name to delete: ");
                    String delName = sc.nextLine();
                    addressBook.deleteContact(delName);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}