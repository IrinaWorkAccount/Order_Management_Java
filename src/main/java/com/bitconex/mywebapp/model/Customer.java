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
    private String customerAddress;

    public Customer(String userLoginName, String userEmail, String userPassword, String customerName, String customerSurname, LocalDate customerBirthDate, String customerAddress) {
        super(userLoginName, userEmail, userPassword, CUSTOMER);
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.customerBirthDate = customerBirthDate;
        this.customerAddress = customerAddress;
    }
    public Customer() {

    }
   /* public Customer(String[] anArray) {
        super(anArray[0],anArray[1],anArray[2], Role.valueOf(anArray[3]));
        this.customerName=anArray[4];
        this.customerSurname=anArray[5];
        this.customerBirthDate= LocalDate.parse(anArray[6]);
        this.customerAddress=anArray[7];
    }*/

    /*public String[] getFullUser() {
        String[] FullCustomerInfo = new String[9];
        FullCustomerInfo[0] = String.valueOf(super.getId());
        FullCustomerInfo[1] = super.getUserLoginName();
        FullCustomerInfo[2] = super.getUserEmail();
        FullCustomerInfo[3] = super.getUserPassword();
        FullCustomerInfo[4] = String.valueOf(super.getRole());
        FullCustomerInfo[5] = customerName;
        FullCustomerInfo[6] = customerSurname;
        FullCustomerInfo[7] = String.valueOf(customerBirthDate);
        FullCustomerInfo[8] = customerAddress;
        return FullCustomerInfo;
    }*/

   /* public String toString(){
        return super.toString()+"customer name : " +customerName+ "customerSurname : " +customerSurname+ "customer birthDate: " +customerBirthDate+ "customer address" +customerAddress;
    }*/

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

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String address) {
        this.customerAddress = address;
    }


    //Methods specific to Customer users
    /*@Override
    public boolean isAdmin() {
        return false; // A customer is not an administrator
    }*/

}
