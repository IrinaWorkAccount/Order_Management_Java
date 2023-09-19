package com.bitconex.mywebapp.model;

import com.bitconex.mywebapp.security.Role;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

import static com.bitconex.mywebapp.security.Role.CUSTOMER;

/**
 * A subclass of "User", which includes additional information for customers, such a name, surname, birthdate and address
 */

@Entity (name = "Customer")
@DiscriminatorValue("CUSTOMER")
public class Customer extends User  {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "name")
    private String customerName;
    @Column(name = "surname")
    private String customerSurname;
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private LocalDate customerBirthDate;

    @Column(name = "address")
    @Embedded
    private CustomerAddress customerAddress;

    public Customer(String userLoginName, String userEmail, String userPassword, String customerName, String customerSurname, LocalDate customerBirthDate, CustomerAddress customerAddress) {
        super(userLoginName, userEmail, userPassword, CUSTOMER);
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.customerBirthDate = customerBirthDate;
        this.customerAddress = customerAddress;
    }
    public Customer() {

    }

    public Long getId() {
        return id;
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

    public LocalDate getCustomerBirthDate() {
        return customerBirthDate;
    }

    public void setCustomerBirthDate(LocalDate birthDate) {
        this.customerBirthDate = birthDate;
    }

    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerAddress CustomerAddress) {
        this.customerAddress = CustomerAddress;
    }


    //Methods specific to Customer users
    /*@Override
    public boolean isAdmin() {
        return false; // A customer is not an administrator
    }*/

}
