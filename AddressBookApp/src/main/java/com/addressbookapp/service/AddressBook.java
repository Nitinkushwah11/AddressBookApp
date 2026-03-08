package com.addressbookapp.service;



import java.util.ArrayList;
import java.util.List;
import com.addressbookapp.model.Contact;

public class AddressBook {

    private List<Contact> contacts = new ArrayList<>();

    // UC-1: Add Contact
    public void addContact(Contact contact) {
        contacts.add(contact);
        System.out.println("Contact added successfully!");
    }

    // UC-2: Display all contacts
    public void displayContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }
        for (Contact c : contacts) {
            System.out.println(c);
        }
    }

    // UC-3: Edit contact by name
    public boolean editContact(String name, Contact updatedContact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().equalsIgnoreCase(name)) {
                contacts.set(i, updatedContact);
                System.out.println("Contact updated successfully!");
                return true;
            }
        }
        System.out.println("Contact not found!");
        return false;
    }

    // UC-4: Delete contact by name
    public boolean deleteContact(String name) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().equalsIgnoreCase(name)) {
                contacts.remove(i);
                System.out.println("Contact deleted successfully!");
                return true;
            }
        }
        System.out.println("Contact not found!");
        return false;
    }
}