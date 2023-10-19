package com.bitconex.mywebapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

import static com.bitconex.mywebapp.security.Role.CUSTOMER;

/**
 * The `Customer` class is a subclass of "User" and represents a customer in the system. It includes additional information such as the customer's name, surname, birthdate, and address.
 */
@Entity
@Table(name = "customers", schema = "public")
@DiscriminatorValue("CUSTOMER")
public class Customer extends User  {
    /*   @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        public Long id;*/
    private String customerName;
    private String customerSurname;
    @JsonIgnore
    @Temporal(TemporalType.DATE)
    private Date customerBirthDate;
    @JsonIgnore
    @Embedded
    private CustomerAddress customerAddress;

    /**
     * Constructs a new customer with the specified properties.
     *
     * @param userLoginName     The login name of the customer.
     * @param userEmail         The email of the customer.
     * @param userPassword      The password of the customer.
     * @param customerName      The first name of the customer.
     * @param customerSurname   The last name of the customer.
     * @param customerBirthDate The birthdate of the customer.
     * @param customerAddress   The address of the customer.
     */
    public Customer(String userLoginName, String userEmail, String userPassword, String customerName, String customerSurname, Date customerBirthDate, CustomerAddress customerAddress) {
        super(userLoginName, userEmail, userPassword, CUSTOMER);
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.customerBirthDate = customerBirthDate;
        this.customerAddress = customerAddress;
    }

    /**
     * Default constructor for the `Customer` class.
     */
    public Customer() {
        //Default constructor
    }

// Getters and setters for class properties
   /*public Long getId() {
        return id;
    }*/

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

    public Date getCustomerBirthDate() {
        return customerBirthDate;
    }

    public void setCustomerBirthDate(Date birthDate) {
        this.customerBirthDate = birthDate;
    }

    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddress = customerAddress;
    }

}
