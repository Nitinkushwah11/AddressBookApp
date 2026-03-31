package com.addressbook.app;

import java.io.*;
import java.util.ArrayList;

public class AddressBookFileIO {
    
    public static void writeToFile(String fileName, AddressBook addressBook) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Contact contact : addressBook.getContacts()) {
                writer.write(contact.getFirstName() + "|" +
                           contact.getLastName() + "|" +
                           contact.getAddress() + "|" +
                           contact.getCity() + "|" +
                           contact.getState() + "|" +
                           contact.getZip() + "|" +
                           contact.getPhoneNumber() + "|" +
                           contact.getEmail());
                writer.newLine();
            }
            System.out.println("Address Book successfully written to " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static AddressBook readFromFile(String fileName) {
        AddressBook addressBook = new AddressBook();
        File file = new File(fileName);
        
        if (!file.exists()) {
            System.out.println("File not found: " + fileName);
            return addressBook;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 8) {
                    Contact contact = new Contact(
                        parts[0], parts[1], parts[2], parts[3],
                        parts[4], parts[5], parts[6], parts[7]
                    );
                    addressBook.getContacts().add(contact);
                }
            }
            System.out.println("Address Book successfully read from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        
        return addressBook;
    }
}