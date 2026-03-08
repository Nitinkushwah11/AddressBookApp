package com.addressbookapp.util;

import com.addressbookapp.model.Contact;
import com.addressbookapp.service.AddressBook;

import java.util.ArrayList;
import java.util.List;

public class AddressBookMain {

    private List<Contact> contacts = new ArrayList<>();

    // UC-1: Add contact with duplicate check
    public void addContact(Contact contact) {
        boolean duplicate = contacts.stream()
                .anyMatch(c -> c.equals(contact)); // check duplicate by name

        if(duplicate) {
            System.out.println("Duplicate contact! Name '" + contact.getName() + "' already exists.");
        } else {
            contacts.add(contact);
            System.out.println("Contact added successfully!");
        }
    }

    // UC-2: View contacts
    public void displayContacts() {
        if(contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }
        contacts.forEach(System.out::println);
    }

    // UC-3: Edit contact
    public boolean editContact(String name, Contact updatedContact) {
        for(int i = 0; i < contacts.size(); i++) {
            if(contacts.get(i).getName().equalsIgnoreCase(name)) {
                contacts.set(i, updatedContact);
                System.out.println("Contact updated successfully!");
                return true;
            }
        }
        System.out.println("Contact not found!");
        return false;
    }

    // UC-4: Delete contact
    public boolean deleteContact(String name) {
        for(int i = 0; i < contacts.size(); i++) {
            if(contacts.get(i).getName().equalsIgnoreCase(name)) {
                contacts.remove(i);
                System.out.println("Contact deleted successfully!");
                return true;
            }
        }
        System.out.println("Contact not found!");
        return false;
    }
}