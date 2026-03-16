package com.addressbookapp.service;


import com.addressbookapp.model.Contact;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBook {

    private List<Contact> contacts = new ArrayList<>();

    public List<Contact> getContacts() { return contacts; }

    public void addContact(Contact contact) {
        boolean duplicate = contacts.stream().anyMatch(c -> c.equals(contact));
        if(duplicate)
            System.out.println("Duplicate contact! Name '" + contact.getName() + "' already exists.");
        else {
            contacts.add(contact);
            System.out.println("Contact added successfully!");
        }
    }

    public void viewContacts() {
        if(contacts.isEmpty()) System.out.println("No contacts found.");
        else contacts.forEach(System.out::println);
    }

    public boolean editContact(String name, Contact updatedContact) {
        for(int i=0; i<contacts.size(); i++) {
            if(contacts.get(i).getName().equalsIgnoreCase(name)) {
                contacts.set(i, updatedContact);
                System.out.println("Contact updated successfully!");
                return true;
            }
        }
        System.out.println("Contact not found!");
        return false;
    }

    public boolean deleteContact(String name) {
        for(int i=0; i<contacts.size(); i++) {
            if(contacts.get(i).getName().equalsIgnoreCase(name)) {
                contacts.remove(i);
                System.out.println("Contact deleted successfully!");
                return true;
            }
        }
        System.out.println("Contact not found!");
        return false;
    }

    public void searchPerson(String city, String state) {
        List<Contact> result = contacts.stream()
                .filter(c -> (city != null && c.getCity().equalsIgnoreCase(city)) ||
                        (state != null && c.getState().equalsIgnoreCase(state)))
                .collect(Collectors.toList());
        if(result.isEmpty()) System.out.println("No person found.");
        else result.forEach(System.out::println);
    }

    public Map<String, List<Contact>> viewPersonsByCity() {
        return contacts.stream().collect(Collectors.groupingBy(Contact::getCity));
    }

    public Map<String, List<Contact>> viewPersonsByState() {
        return contacts.stream().collect(Collectors.groupingBy(Contact::getState));
    }

    public Map<String, Long> countByCity() {
        return contacts.stream().collect(Collectors.groupingBy(Contact::getCity, Collectors.counting()));
    }

    public Map<String, Long> countByState() {
        return contacts.stream().collect(Collectors.groupingBy(Contact::getState, Collectors.counting()));
    }

    public List<Contact> sortByName() {
        return contacts.stream()
                .sorted((c1,c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
                .collect(Collectors.toList());
    }

    public void writeContactsToFile(String fileName) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for(Contact c : contacts) {
                writer.write(c.getName() + "," + c.getPhone() + "," + c.getEmail() + "," +
                        c.getCity() + "," + c.getState());
                writer.newLine();
            }
            System.out.println("Contacts written to file successfully.");
        } catch(IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public void readContactsFromFile(String fileName) {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if(data.length < 5) continue;
                Contact contact = new Contact(data[0], data[1], data[2], data[3], data[4]);
                contacts.add(contact);
            }
            System.out.println("Contacts loaded from file.");
        } catch(IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void readContactsFromCSV(String fileName) {
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            String[] nextLine;
            reader.readNext(); // skip header
            while ((nextLine = reader.readNext()) != null) {
                if(nextLine.length < 5) continue;
                Contact contact = new Contact(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4]);
                contacts.add(contact);
            }
            System.out.println("Contacts loaded from CSV successfully!");
        } catch (IOException | CsvValidationException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
    }
    public void writeContactsToCSV(String fileName) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            // Header
            String[] header = {"Name", "Phone", "Email", "City", "State"};
            writer.writeNext(header);

            for (Contact c : contacts) {
                String[] data = {c.getName(), c.getPhone(), c.getEmail(), c.getCity(), c.getState()};
                writer.writeNext(data);
            }
            System.out.println("Contacts saved to CSV successfully!");
        } catch (IOException e) {
            System.out.println("Error writing CSV: " + e.getMessage());
        }
    }
}