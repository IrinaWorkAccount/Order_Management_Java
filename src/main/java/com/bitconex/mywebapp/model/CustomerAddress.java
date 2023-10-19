package com.bitconex.mywebapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * The `CustomerAddress` class represents the address information of a customer, including street, zip code, city, and country.
 */
@Embeddable
public class CustomerAddress {
    @Column(name = "street")
    private String street;
    @Column(name = "zipCode")
    private String zipCode;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;

    /**
     * Constructs a new customer address with the specified properties.
     *
     * @param street  The street address.
     * @param zipCode The zip code of the address.
     * @param city    The city of the address.
     * @param country The country of the address.
     */
    public CustomerAddress(String street, String zipCode, String city, String country) {
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    /**
     * Default constructor for the `CustomerAddress` class.
     */
    public CustomerAddress() {
        //Default constructor
    }

    // Getters and setters for class properties
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}

