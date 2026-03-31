package com.addressbook.app;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AddressBookCSVIO {
    
    public static void writeToCSV(String fileName, AddressBook addressBook) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            // Write header
            String[] header = {"FirstName", "LastName", "Address", "City", "State", "Zip", "PhoneNumber", "Email"};
            writer.writeNext(header);
            
            // Write contact data
            for (Contact contact : addressBook.getContacts()) {
                String[] data = {
                    contact.getFirstName(),
                    contact.getLastName(),
                    contact.getAddress(),
                    contact.getCity(),
                    contact.getState(),
                    contact.getZip(),
                    contact.getPhoneNumber(),
                    contact.getEmail()
                };
                writer.writeNext(data);
            }
            System.out.println("Address Book successfully written to CSV file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    public static AddressBook readFromCSV(String fileName) {
        AddressBook addressBook = new AddressBook();
        
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            List<String[]> records = reader.readAll();
            
            // Skip header row if present
            boolean firstRow = true;
            for (String[] record : records) {
                if (firstRow) {
                    firstRow = false;
                    continue;
                }
                
                if (record.length >= 8) {
                    Contact contact = new Contact(
                        record[0], record[1], record[2], record[3],
                        record[4], record[5], record[6], record[7]
                    );
                    addressBook.getContacts().add(contact);
                }
            }
            System.out.println("Address Book successfully read from CSV file: " + fileName);
        } catch (IOException | CsvException e) {
            System.out.println("Error reading from CSV file: " + e.getMessage());
        }
        
        return addressBook;
    }
}
