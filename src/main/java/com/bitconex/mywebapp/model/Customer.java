package com.bitconex.mywebapp.model;

import jakarta.persistence.*;

/**
 * A subclass of "User", wich includes additional information for customers, such a name, surname, birth date and adress
 */

@Entity
public class Customer extends User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String customerName;
    @Column(name = "surname")
    private String customerSurname;
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private String customerBirthDate;
    @Column(name = "address")
    private String customerAddress;

    public Customer(String userLoginName, String userEmail, String userPassword, String customerName, String customerSurname, String customerBirthDate, String customerAddress) {
        super(userLoginName, userEmail, userPassword);
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.customerBirthDate = customerBirthDate;
        this.customerAddress = customerAddress;
    }

    public Customer() {
        super();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String name) {
        this.customerName = name;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String surname) {
        this.customerSurname = surname;
    }

    public String getCustomerBirthDate() {
        return customerBirthDate;
    }

    public void setCustomerBirthDate(String birthDate) {
        this.customerBirthDate = birthDate;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String address) {
        this.customerAddress = address;
    }

    //Methods specific to Customer users

}
