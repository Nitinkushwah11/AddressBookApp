package com.addressbookapp.model;

//model folder means what info what application stores
public class Contact {
  String firstName;
  String lastName;
  String address;
  String city;
  String state;
  String zip;
  String phoneNumber;
  String email;

  public Contact(String firstName , String lastName , String address , String city , String state , String zip , String phoneNumber , String email){
      this.firstName = firstName;
      this.lastName = lastName;
      this.address = address;
      this.city = city;
      this.state = state;
      this.zip = zip;
      this.phoneNumber = phoneNumber;
      this.email = email;
  }

  public Contact(String newName, String newPhone, String newEmail) {
      this.firstName = newName;
      this.phoneNumber = newPhone;
      this.email = newEmail;
  }

  public void display(){
      System.out.println(firstName + " " + lastName + " " + address + " "
              + city + " " + state + " " + zip + " " + phoneNumber + " " + email);
  }

  public String getFirstName(){
      return firstName;
  }

  public String getLastName(){
      return  lastName;
  }

  public String getName() {
      return firstName;
  }
}