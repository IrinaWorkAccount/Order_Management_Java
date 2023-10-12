package com.bitconex.mywebapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import static com.bitconex.mywebapp.security.Role.CUSTOMER;

/**
 * A subclass of "User", which includes additional information for customers, such a name, surname, birthdate and address
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
    private LocalDate customerBirthDate;

    @JsonIgnore
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
        //Default constructor
    }

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

    public LocalDate getCustomerBirthDate() {
        return customerBirthDate;
    }

    public void setCustomerBirthDate(LocalDate birthDate) {
        this.customerBirthDate = birthDate;
    }

    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddress = customerAddress;
    }

}
