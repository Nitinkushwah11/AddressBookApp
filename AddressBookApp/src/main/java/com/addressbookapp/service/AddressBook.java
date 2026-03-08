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
}