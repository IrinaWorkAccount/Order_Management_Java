package com.bitconex.mywebapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * A subclass of "Users", which includes additional information for admin users.
 */

@Entity
public class Admin extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Admin(String loginName, String email, String password) {
        super(loginName, email, password);
    }

    public Admin() {
        super();
        //TODO?
    }

    //Methods specific to Admin users

}