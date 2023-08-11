package com.bitconex.mywebapp.model;

/**
 * A subclass of "User", wich includes additional information for customers, such a name, surname, birth date and adress
 */

public class Customer extends User {
    private String name;
    private String surname;
    private String birthDate;
    private String address;

    public Customer(String loginName, String email, String password, String name, String surname, String birthDate, String address) {
        super(loginName, email, password);
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.address = address;
    }

    public String getCustomerName() {
        return name;
    }

    public void setCustomerName(String name) {
        this.name = name;
    }

    public String getCustomerSurname() {
        return surname;
    }

    public void setCustomerSurname(String surname) {
        this.surname = surname;
    }

    public String getCustomerBirthDate() {
        return birthDate;
    }

    public void setCustomerBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCustomerAddress() {
        return address;
    }

    public void setCustomerAddress(String address) {
        this.address = address;
    }

}
