package com.addressbookapp.util;

import com.addressbookapp.model.Contact;
import com.addressbookapp.service.AddressBook;

import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.*;

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
            int choice = sc.nextInt(); sc.nextLine();

            switch(choice) {
                case 1:
                    System.out.println("Enter Address Book Name:");
                    String bookName = sc.nextLine();
                    addressBooks.put(bookName, new AddressBook());
                    System.out.println("Address Book Created Successfully!");
                    break;

                case 2:
                    System.out.println("Enter Address Book Name:");
                    String name = sc.nextLine();
                    AddressBook currentBook = addressBooks.get(name);

                    if(currentBook == null) {
                        System.out.println("Address Book Not Found!");
                        break;
                    }

                    while(true) {
                        System.out.println("\n1. Add Contact");
                        System.out.println("2. View Contacts");
                        System.out.println("3. Search Person by City/State");
                        System.out.println("4. Delete Contact");
                        System.out.println("5. Back to Main Menu");
                        System.out.println("6. View Persons by City");
                        System.out.println("7. View Persons by State");
                        System.out.println("8. Count Contacts by City");
                        System.out.println("9. Count Contacts by State");
                        System.out.println("10. Sort Contacts by Name");
                        System.out.println("11. Write Contacts to File");
                        System.out.println("12. Read Contacts from File");

                        int op = sc.nextInt(); sc.nextLine();

                        switch(op) {
                            case 1:
                                System.out.println("Enter Name:"); String nameInput = sc.next();
                                System.out.println("Enter Phone:"); String phone = sc.next();
                                System.out.println("Enter Email:"); String email = sc.next();
                                System.out.println("Enter City:"); String city = sc.next();
                                System.out.println("Enter State:"); String state = sc.next();
                                Contact contact = new Contact(nameInput, phone, email, city, state);
                                currentBook.addContact(contact);
                                break;
                            case 2: currentBook.viewContacts(); break;
                            case 3:
                                System.out.println("Enter City:"); String searchCity = sc.next();
                                System.out.println("Enter State:"); String searchState = sc.next();
                                currentBook.searchPerson(searchCity, searchState);
                                break;
                            case 4:
                                System.out.println("Enter Name to Delete:"); String deleteName = sc.next();
                                currentBook.deleteContact(deleteName);
                                break;
                            case 5: break;
                            case 6:
                                Map<String,List<Contact>> cityMap = currentBook.viewPersonsByCity();
                                cityMap.forEach((cityKey, persons) -> {
                                    System.out.println("\nCity: "+cityKey);
                                    persons.forEach(System.out::println);
                                }); break;
                            case 7:
                                Map<String,List<Contact>> stateMap = currentBook.viewPersonsByState();
                                stateMap.forEach((stateKey, persons) -> {
                                    System.out.println("\nState: "+stateKey);
                                    persons.forEach(System.out::println);
                                }); break;
                            case 8:
                                Map<String,Long> cityCount = currentBook.countByCity();
                                cityCount.forEach((k,v) -> System.out.println(k+" : "+v)); break;
                            case 9:
                                Map<String,Long> stateCount = currentBook.countByState();
                                stateCount.forEach((k,v) -> System.out.println(k+" : "+v)); break;
                            case 10:
                                List<Contact> sortedContacts = currentBook.sortByName();
                                sortedContacts.forEach(System.out::println); break;
                            case 11:
                                System.out.println("Enter filename to save:"); String saveFile = sc.next();
                                currentBook.writeContactsToFile(saveFile); break;
                            case 12:
                                System.out.println("Enter filename to load:"); String loadFile = sc.next();
                                currentBook.readContactsFromFile(loadFile); break;
                            default: System.out.println("Invalid Option");
                        }
                        if(op == 5) break;
                    }
                    break;

                case 3:
                    System.out.println("Enter City:"); String city = sc.next();
                    System.out.println("Enter State:"); String state = sc.next();
                    addressBooks.values().stream()
                            .flatMap(book -> book.getContacts().stream())
                            .filter(p -> p.getCity().equalsIgnoreCase(city) || p.getState().equalsIgnoreCase(state))
                            .forEach(System.out::println);
                    break;

                case 4: System.out.println("Exiting Program..."); return;
                default: System.out.println("Invalid Choice");
            }
        }
    }
}