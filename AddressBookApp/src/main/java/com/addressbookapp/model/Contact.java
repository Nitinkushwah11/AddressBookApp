package com.addressbookapp.model;


import java.util.Objects;

public class Contact {

    private String name;
    private String phone;
    private String email;
    private String city;
    private String state;
    private String zip;

    public Contact(String name, String phone, String email, String city, String state, String zip) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    // Getters and setters
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getCity() { return city; }
    public String getState() { return state; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public String getZip() {
        return zip;
    }
    @Override
    public String toString() {
        return "Name: " + name +
                ", Phone: " + phone +
                ", Email: " + email +
                ", City: " + city +
                ", State: " + state +
                ", Zip: " + zip;
    }

    // Equals & hashCode for duplicate check by name
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Contact)) return false;
        Contact c = (Contact) o;
        return this.name.equalsIgnoreCase(c.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}