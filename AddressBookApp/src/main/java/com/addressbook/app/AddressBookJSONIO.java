package com.addressbook.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddressBookJSONIO {
    
    public static void writeToJSON(String fileName, AddressBook addressBook) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(addressBook.getContacts(), writer);
            System.out.println("Address Book successfully written to JSON file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing to JSON file: " + e.getMessage());
        }
    }

    public static AddressBook readFromJSON(String fileName) {
        AddressBook addressBook = new AddressBook();
        File file = new File(fileName);
        
        if (!file.exists()) {
            System.out.println("File not found: " + fileName);
            return addressBook;
        }

        try (Reader reader = new FileReader(fileName)) {
            Gson gson = new Gson();
            Type contactListType = new TypeToken<ArrayList<Contact>>(){}.getType();
            ArrayList<Contact> contacts = gson.fromJson(reader, contactListType);
            
            if (contacts != null) {
                for (Contact contact : contacts) {
                    addressBook.getContacts().add(contact);
                }
            }
            System.out.println("Address Book successfully read from JSON file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading from JSON file: " + e.getMessage());
        }
        
        return addressBook;
    }
}