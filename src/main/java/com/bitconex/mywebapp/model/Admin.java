package com.bitconex.mywebapp.model;

import jakarta.persistence.*;

import static com.bitconex.mywebapp.security.Role.ADMIN;

/**
 * A subclass of "Users", which includes additional information for admin users.
 */

@Entity (name = "Admin")
@DiscriminatorValue("ADMIN")
public class Admin extends User{

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Admin(String userLoginName, String userEmail, String userPassword) {
        super(userLoginName, userEmail, userPassword, ADMIN);
    }

    public Admin() {
        //Default constructor
    }

    public Long getId() {
        return id;
    }

    //Methods specific to Admin users
    @Override
    public boolean isAdmin() {
        return true; // An admin is always an administrator
    }

}