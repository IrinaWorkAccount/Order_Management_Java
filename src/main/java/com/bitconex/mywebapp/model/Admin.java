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

    public Admin(String userLoginName, String userEmail, String userPassword) {
        super(userLoginName, userEmail, userPassword);
    }

    public Admin() {
        super();
    }

    //Methods specific to Admin users

}