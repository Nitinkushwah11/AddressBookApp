package com.addressbook.app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AddressBook {
    private ArrayList<Contact> contacts;

    public AddressBook() {
        this.contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        boolean isDuplicate = contacts.stream()
            .anyMatch(c -> c.equals(contact));
        
        if (isDuplicate) {
            System.out.println("Duplicate contact! A person with name '" + 
                contact.getFirstName() + " " + contact.getLastName() + "' already exists.");
        } else {
            contacts.add(contact);
            System.out.println("Contact added successfully!");
        }
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void displayContact() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found!");
        } else {
            System.out.println("\nAll Contacts:");
            for (int i = 0; i < contacts.size(); i++) {
                System.out.println("\n--- Contact " + (i + 1) + " ---");
                System.out.println(contacts.get(i));
            }
        }
    }

    public boolean editContact(String firstName, String lastName, Contact updatedContact) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            if (contact.getFirstName().equalsIgnoreCase(firstName) && contact.getLastName().equalsIgnoreCase(lastName)) {
                contacts.set(i, updatedContact);
                System.out.println("Contact updated successfully!");
                return true;
            }
        }
        System.out.println("Contact not found!");
        return false;
    }

    public boolean deleteContact(String firstName, String lastName) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            if (contact.getFirstName().equalsIgnoreCase(firstName) && contact.getLastName().equalsIgnoreCase(lastName)) {
                contacts.remove(i);
                System.out.println("Contact deleted successfully!");
                return true;
            }
        }
        System.out.println("Contact not found!");
        return false;
    }

    public List<Contact> sortByName() {
        return contacts.stream()
            .sorted(Comparator.comparing(Contact::getFirstName, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Contact::getLastName, String.CASE_INSENSITIVE_ORDER))
            .collect(Collectors.toList());
    }

    public void displaySortedByName() {
        List<Contact> sortedContacts = sortByName();
        if (sortedContacts.isEmpty()) {
            System.out.println("No contacts found!");
        } else {
            System.out.println("\nAll Contacts (Sorted by Name):");
            for (int i = 0; i < sortedContacts.size(); i++) {
                System.out.println("\n--- Contact " + (i + 1) + " ---");
                System.out.println(sortedContacts.get(i));
            }
        }
    }

    public List<Contact> sortByCity() {
        return contacts.stream()
            .sorted(Comparator.comparing(Contact::getCity, String.CASE_INSENSITIVE_ORDER))
            .collect(Collectors.toList());
    }

    public void displaySortedByCity() {
        List<Contact> sortedContacts = sortByCity();
        if (sortedContacts.isEmpty()) {
            System.out.println("No contacts found!");
        } else {
            System.out.println("\nAll Contacts (Sorted by City):");
            for (int i = 0; i < sortedContacts.size(); i++) {
                System.out.println("\n--- Contact " + (i + 1) + " ---");
                System.out.println(sortedContacts.get(i));
            }
        }
    }

    public List<Contact> sortByState() {
        return contacts.stream()
            .sorted(Comparator.comparing(Contact::getState, String.CASE_INSENSITIVE_ORDER))
            .collect(Collectors.toList());
    }

    public void displaySortedByState() {
        List<Contact> sortedContacts = sortByState();
        if (sortedContacts.isEmpty()) {
            System.out.println("No contacts found!");
        } else {
            System.out.println("\nAll Contacts (Sorted by State):");
            for (int i = 0; i < sortedContacts.size(); i++) {
                System.out.println("\n--- Contact " + (i + 1) + " ---");
                System.out.println(sortedContacts.get(i));
            }
        }
    }

    public List<Contact> sortByZip() {
        return contacts.stream()
            .sorted(Comparator.comparing(Contact::getZip, String.CASE_INSENSITIVE_ORDER))
            .collect(Collectors.toList());
    }

    public void displaySortedByZip() {
        List<Contact> sortedContacts = sortByZip();
        if (sortedContacts.isEmpty()) {
            System.out.println("No contacts found!");
        } else {
            System.out.println("\nAll Contacts (Sorted by Zip):");
            for (int i = 0; i < sortedContacts.size(); i++) {
                System.out.println("\n--- Contact " + (i + 1) + " ---");
                System.out.println(sortedContacts.get(i));
            }
        }
    }
}