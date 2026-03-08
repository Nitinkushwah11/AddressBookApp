package com.addressbookapp.service;

//AddressBook
//|
//| has many
//↓
//Contact

//This is HAS-A relationship (Aggregation).
import com.addressbookapp.model.Contact;

import java.util.ArrayList;

public class AddressBook {
ArrayList<Contact> contacts = new ArrayList<>();
public void addContact(Contact contact){
 contacts.add(contact);
}

public void displayContacts(){
 for(Contact c : contacts){
     c.display();
 }
}

public boolean editContact(String firstName, String lastName, Contact updatedContact) {
    for (int i = 0; i < contacts.size(); i++) {
        Contact c = contacts.get(i);
        if (c.getFirstName().equalsIgnoreCase(firstName)
                && c.getLastName().equalsIgnoreCase(lastName)) {
            contacts.set(i, updatedContact);
            return true;
        }
    }
    return false;
}
}